package thread.executor.test;

public class OldOrderServiceTestMain {

    public static void main(String[] args) {
        String orderNo = "Order#1234"; // 예시 주문번
        OldOrderService orderService = new OldOrderService();
        orderService.older(orderNo);
    }
}
