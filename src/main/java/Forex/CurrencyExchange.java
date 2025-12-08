package Forex;

public class CurrencyExchange {

    private static final double[][] exchangeRates = {
            //USD      JPY      KRW        EUR       GBP      CNY      CHF      AUD      CAD      PHP
            {1.0,    154.08,   1428.24,   0.8660,  0.7609,  7.1216,  0.8040,  1.5281,  1.4005,  58.7215}, //USD
            {0.0065, 1.0,      9.2721,    0.0056,  0.0049,  0.0462,  0.0052,  0.0099,  0.0091,  0.3812}, //JPY
            {0.0007, 0.1078,   1.0,       0.0006,  0.0005,  0.0050,  0.0006,  0.0011,  0.0010,  0.0411}, //KRW
            {1.1547, 177.92,   1649.31,   1.0,     0.8787,  8.2235,  0.9284,  1.7648,  1.6173,  67.8098}, //EUR
            {1.3142, 202.45,   1877.16,   1.1380,  1.0,     9.3580,  1.0566,  2.0082,  1.8405,  77.1778}, //GBP
            {0.1405, 21.6427,  200.58,    0.1216,  0.1069,  1.0,     0.1129,  0.2146,  0.1967,  8.2465}, //CNY
            {1.2438, 191.63,   1776.42,   1.0770,  0.9464,  8.8557,  1.0,     1.9003,  1.7418,  73.0318}, //CHF
            {0.6544, 100.82,   934.49,    0.5667,  0.4980,  4.6605,  0.5263,  1.0,     0.9165,  38.4293}, //AUD
            {0.7140, 110.00,   1019.54,   0.6183,  0.5434,  5.0843,  0.5742,  1.0911,  1.0,     41.9285}, //CAD
            {0.0170, 2.6237,   24.3185,   0.0147,  0.0130,  0.1213,  0.0137,  0.0260,  0.0238,  1.0} //PHP
    };

    //
    public static double convert(double amount, String sourceCurrency, String targetCurrency) {
        int sourceIndex = getCurrencyIndex(sourceCurrency);
        int targetIndex = getCurrencyIndex(targetCurrency);
        return amount * exchangeRates[sourceIndex][targetIndex];
    }

    public static int getCurrencyIndex(String currency) {
        switch (currency) {
            case "USD":
                return 0;

            case "JPY":
                return 1;

            case "KRW":
                return 2;

            case "EUR":
                return 3;

            case "GBP":
                return 4;

            case "CNY":
                return 5;

            case "CHF":
                return 6;

            case "AUD":
                return 7;

            case "CAD":
                return 8;

            case "PHP":
                return 9;

            default:
                throw new IllegalArgumentException("Unknown Currency: " + currency);
        }
    }
}