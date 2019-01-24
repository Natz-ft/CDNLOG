package netflow;

import TOOLS.getDate;

public class cdn_netflow_acc_day {
	public static void main(String[] args) throws Exception {
		String yesterday = "";
		if (args.length < 1) {
			yesterday = getDate.getYesterday();
		} else {
			yesterday = args[0];
		}
		netflowInToDB.IntoDB_CDN(yesterday);
		netflowInToDB.IntoDB_cache(yesterday);
		netflowInToDB.IntoDB_OTT(yesterday);
		//netflowInToDB.IntoDB_OTT_CITY(yesterday);
		netflowInToDB.IntoDB_WEB_CDN_HW(yesterday);
		netflowInToDB.IntoDB_WEB_CDN_ZTE(yesterday);
		netflowInToDB.IntoDB_cache_hw_city(yesterday);

	}

}
