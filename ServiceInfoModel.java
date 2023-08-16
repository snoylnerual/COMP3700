public class ServiceInfoModel {
    public static int PRODUCT_INFO_SERVICE = 1;
    public static int ORDER_INFO_SERVICE = 2;
    public static int PRODUCT_PRICE_UPDATE_SERVICE = 3;
    public static int PRODUCT_QUANTITY_UPDATE_SERVICE = 4;
    public static int USER_PASS_PAIR_VALID_SERVICE = 5;
    public static int USER_PASS_ORDER_CANCEL_SERVICE = 6;

    public int serviceCode;
    public String serviceHostAddress;

    public int serviceHostPort;

}
