package com.esoe.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ParseAssets {
	private static final Gson gson = new Gson();
	private static Database db = new Database();

	// TODO: update Insert method
	public static void parseOriginalData() throws FileNotFoundException {
		String dataDir = System.getProperty("user.dir") + "/assets";
		parseStation(dataDir + "/station.json");
		parseUniversityDiscount(dataDir + "/universityDiscount.json");
		parseEarlyDiscount(dataDir + "/earlyDiscount.json");
		parseTimeTable(dataDir + "/timeTable.json");
		parsePrice(dataDir + "/price_modified.json");
	}

	public static void parsePrice(String dataPath) {
		try {
			JsonObject[] obj = gson.fromJson(new FileReader(dataPath), JsonObject[].class);
			for (JsonObject o : obj) {
				String start_station_id = o.get("OriginStationID").getAsString();
				String dest_station_id = o.get("DesrinationStationsID").getAsString();
				String direction = o.get("Direction").getAsString();
				String ticket_type = o.get("TicketType").getAsString();
				String price = o.get("Price").getAsString();
				if (ticket_type.length() == 2) {
					ticket_type = "P" + ticket_type;
				}
				final String col = "start_station_id, dest_station_id, direction, ticket_type, price";
				String args = String.format("'%s', '%s', '%s', '%s', '%s'",
					start_station_id, dest_station_id, direction, ticket_type, price);
				db.insert("Price", col,args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  void parseTimeTable(String dataPath) {
		try {
			final Integer[] squence_NtoS = {990, 1000, 1010, 1020, 1030, 1035, 1040, 1043, 1047, 1050, 1060, 1070};

			JsonObject[] obj = gson.fromJson(new FileReader(dataPath), JsonObject[].class);

			int count = 1;
			for (JsonObject o : obj) {
				String UpdateTime = o.get("UpdateTime").getAsString();
				String EffectiveDate = o.get("EffectiveDate").getAsString();

				JsonObject train = (JsonObject) o.get("GeneralTimetable").getAsJsonObject().get("GeneralTrainInfo");
				String trainID = train.get("TrainNo").getAsString();
				String originID = train.get("StartingStationID").toString();
				String destinationID = train.get("EndingStationID").toString();
				String direction = train.get("Direction").toString();

				JsonObject day = (JsonObject) o.get("GeneralTimetable").getAsJsonObject().get("ServiceDay");
				String serviceDay = new StringBuilder(day.toString().replaceAll("[^0-9]", "")).toString();

				JsonArray stopTimes = (JsonArray) o.get("GeneralTimetable").getAsJsonObject().get("StopTimes");
				String[] sequenceArray = new String[12];
				Map<Integer, Integer> stationMap = new HashMap<>();
				for (int i = 0; i < 12; i++) {
					stationMap.put(squence_NtoS[i], i);
				}
				for (int i = 0; i < stopTimes.size(); i++) {
					Integer no = stationMap.get(Integer.valueOf(stopTimes.get(i).getAsJsonObject().get("StationID").getAsString()));
					sequenceArray[no] = stopTimes.get(i).getAsJsonObject().get("DepartureTime").getAsString();
				}
				for (int i = 0; i < 12; i++) {
					if (sequenceArray[i] == null) {
						sequenceArray[i] = "00:00";
					}
				}

				String sequence = String.join("', '", sequenceArray);
				String args2 = String.format("'%s', '%s', '%s'", count, count, sequence);
				db.insert("TripSchedule", args2);
				String args = String.format("'%s', '%s', %s, %s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'",
					count, trainID, originID, destinationID, direction, UpdateTime, EffectiveDate,
					serviceDay.charAt(0), serviceDay.charAt(1), serviceDay.charAt(2), serviceDay.charAt(3), serviceDay.charAt(4), serviceDay.charAt(5), serviceDay.charAt(6));
				db.insert("Trip", args);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  void parseStation(String dataPath) {
		try {
			JsonObject[] obj = gson.fromJson(new FileReader(dataPath), JsonObject[].class);
			for (JsonObject o : obj) {
				String name_En = o.get("StationName").getAsJsonObject().get("En").getAsString();
				String name_Zh_tw = o.get("StationName").getAsJsonObject().get("Zh_tw").getAsString();
				String stationAddress = o.get("StationAddress").getAsString();
				String stationID = o.get("StationID").getAsString();
				String args =
					String.format(
						"'%s', '%s', '%s', '%s'", stationID, name_En, name_Zh_tw, stationAddress);
				db.insert("Station", args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  void parseEarlyDiscount(String dataPath) throws FileNotFoundException {
		final String columns =
			"train_id, discount_type, update_date, effective_date, expire_date, weekday, percentage, quantity";
		final String[] DAY_OF_WEEK = {
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
		};
		JsonObject obj = gson.fromJson(new FileReader(dataPath), JsonObject.class);
		String UpdateTime = obj.get("UpdateTime").toString();
		String EffctiveDate = obj.get("EffectiveDate").toString();
		String ExpireDate = obj.get("ExpiringDate").toString();
		JsonArray jsonArray = obj.get("DiscountTrains").getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			String trainNo = jsonArray.get(i).getAsJsonObject().get("TrainNo").getAsString();
			JsonObject discounts =
				jsonArray.get(i).getAsJsonObject().get("ServiceDayDiscount").getAsJsonObject();
			for (int j = 0; j < discounts.size(); j++) {
				String discount = discounts.get(DAY_OF_WEEK[j]).toString();
				Pattern pd = Pattern.compile("0.[0-9]{1,2}");
				Matcher md = pd.matcher(discount);
				Pattern pt = Pattern.compile(":[0-9]{2,}");
				Matcher mt = pt.matcher(discount);
				while (md.find() && mt.find()) {
					String quantity = mt.group().substring(1);
					String percentage = md.group().substring(2);
					if (percentage.length() == 1) {
						percentage = percentage.concat("0");
					}
					final String[] weekday = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
					String args = String.format(
						"'%s', 'EARLY', %s, %s, %s, '%s', '%s', '%s'",
						trainNo,
						UpdateTime,
						EffctiveDate,
						ExpireDate,
						weekday[j],
						percentage,
						quantity);
					db.insert("Discount", columns, args);
				}
			}
		}
	}

	public static  void parseUniversityDiscount(String dataPath) throws FileNotFoundException {

		final String[] DAY_OF_WEEK = {
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
		};
		final String[] weekday = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
		JsonObject obj = gson.fromJson(new FileReader(dataPath), JsonObject.class);
		String UpdateTime = obj.get("UpdateTime").toString();
		String EffctiveDate = obj.get("EffectiveDate").toString();
		String ExpireDate = obj.get("ExpiringDate").toString();
		JsonArray jsonArray = obj.get("DiscountTrains").getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			String trainNo = jsonArray.get(i).getAsJsonObject().get("TrainNo").getAsString();
			JsonObject discounts =
				jsonArray.get(i).getAsJsonObject().get("ServiceDayDiscount").getAsJsonObject();
			for (int j = 0; j < discounts.size(); j++) {
				String discount = discounts.get(DAY_OF_WEEK[j]).toString();
				if (discount.length() > 1) {
					discount = discount.substring(2);
					if (discount.length() == 1) {
						discount = discount.concat("0");
					}
				}
				final String columns =
					"train_id, discount_type, update_date, effective_date, expire_date, weekday, percentage, quantity";
				if (discount.length() > 1) {
					String args =
						String.format(
							"'%s', 'UNIVERSITY', %s, %s, %s, '%s', '%s', '32767'",
							trainNo,
							UpdateTime,
							EffctiveDate,
							ExpireDate,
							weekday[j],
							discount);
					db.insert("Discount", columns, args);
				}
			}
		}
	}

}