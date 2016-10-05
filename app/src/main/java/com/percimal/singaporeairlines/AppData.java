package com.percimal.singaporeairlines;

import java.util.HashMap;

public final class AppData {
    private static AppData mInstance = null;

    HashMap<String, String> iataCodeToCity;

    protected AppData() {
        iataCodeToCity = new HashMap<>();
        iataCodeToCity.put("ADL", "Adelaide");
        iataCodeToCity.put("AKL", "Auckland");
        iataCodeToCity.put("AMD", "Ahmedabad");
        iataCodeToCity.put("AMS", "Amsterdam");
        iataCodeToCity.put("BCN", "Barcelona");
        iataCodeToCity.put("BKK", "Bangkok");
        iataCodeToCity.put("BLR", "Bangalore");
        iataCodeToCity.put("BNE", "Brisbane");
        iataCodeToCity.put("BOM", "Mumbai");
        iataCodeToCity.put("BWN", "Bandar Seri Begawan");
        iataCodeToCity.put("CAN", "Guangzhou");
        iataCodeToCity.put("CBR", "Canberra");
        iataCodeToCity.put("CCU", "Kolkata");
        iataCodeToCity.put("CDG", "Paris");
        iataCodeToCity.put("CGK", "Jakarta");
        iataCodeToCity.put("CHC", "Christchurch");
        iataCodeToCity.put("CMB", "Colombo");
        iataCodeToCity.put("CPH", "Copenhagen");
        iataCodeToCity.put("CPT", "Cape Town");
        iataCodeToCity.put("DAC", "Dhaka");
        iataCodeToCity.put("DEL", "Delhi");
        iataCodeToCity.put("DME", "Moscow");
        iataCodeToCity.put("DPS", "Bali");
        iataCodeToCity.put("DRW", "Darwin");
        iataCodeToCity.put("DUS", "Dusseldorf");
        iataCodeToCity.put("DXB", "Dubai");
        iataCodeToCity.put("FCO", "Rome");
        iataCodeToCity.put("FRA", "Frankfurt");
        iataCodeToCity.put("FUK", "Fukuoka");
        iataCodeToCity.put("GRU", "Sao-Paolo");
        iataCodeToCity.put("HAN", "Hanoi");
        iataCodeToCity.put("HKG", "Hong Kong");
        iataCodeToCity.put("IAH", "Houston");
        iataCodeToCity.put("ICN", "Seoul");
        iataCodeToCity.put("IST", "Istanbul");
        iataCodeToCity.put("JFK", "New York");
        iataCodeToCity.put("JNB", "Johannesburg");
        iataCodeToCity.put("KIX", "Osaka");
        iataCodeToCity.put("KUL", "Kuala Lumpur");
        iataCodeToCity.put("LAX", "Los Angeles");
        iataCodeToCity.put("LHR", "London");
        iataCodeToCity.put("MAA", "Chennai");
        iataCodeToCity.put("MAN", "Manchester");
        iataCodeToCity.put("MEL", "Melbourne");
        iataCodeToCity.put("MLE", "Male");
        iataCodeToCity.put("MNL", "Manila");
        iataCodeToCity.put("MUC", "Munich");
        iataCodeToCity.put("MXP", "Milan");
        iataCodeToCity.put("NGO", "Nagoya");
        iataCodeToCity.put("NRT", "Tokyo");
        iataCodeToCity.put("PEK", "Beijing");
        iataCodeToCity.put("PER", "Perth");
        iataCodeToCity.put("PVG", "Shanghai");
        iataCodeToCity.put("RGN", "Yangon");
        iataCodeToCity.put("SFO", "San Francisco");
        iataCodeToCity.put("SGN", "Ho Chi Minh City");
        iataCodeToCity.put("SIN", "Singapore");
        iataCodeToCity.put("SUB", "Surabaya");
        iataCodeToCity.put("SYD", "Sydney");
        iataCodeToCity.put("TPE", "Taipei");
        iataCodeToCity.put("WLG", "Wellington");
        iataCodeToCity.put("ZRH", "Zurich");
    }

    public static synchronized AppData getInstance() {
        if (null == mInstance) {
            mInstance = new AppData();
        }
        return mInstance;
    }

}
