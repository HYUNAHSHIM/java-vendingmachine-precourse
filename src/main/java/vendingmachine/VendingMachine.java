package vendingmachine;

import java.util.ArrayList;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;

public class VendingMachine {
	private ArrayList<Product> products = null;

	public VendingMachine(int amount) {
		getRandomCoin(1, 1);
		for (Coin c: Coin.values()) {
			if (c.getAmount() == 10) {
				c.increaseCount(amount / c.getAmount());
				break;
			}
			int num = getRandomCoin(0, amount / c.getAmount());
			c.increaseCount(num);
			amount -= c.getAmount() * num;
		}
	}

	public int getRandomCoin(int start, int end) {
		List<Integer> li = new ArrayList<>();
		for (int i = 0; i < end + 1; i++) {
			li.add(start + i);
		}
		return Randoms.pickNumberInList(li);
	}

	public void setInitProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public void buyProducts(int inputCoin) {
		while (!isFinished(inputCoin)) {
			System.out.println("\n투입 금액: " + inputCoin + "원");
			System.out.println(Constants.productNameMsg);
			String name = getProductName();

		}
	}

	public String getProductName() {
		while (true) {
			try {
				String input = camp.nextstep.edu.missionutils.Console.readLine();
				checkProductExist(input);
				return input;
			} catch (IllegalArgumentException exception) {
				System.out.println(exception.getMessage());
				continue;
			}
		}
	}

	public void checkProductExist(String input) {
		for (Product p: this.products) {
			if (p.getName().equals(input)) {
				return;
			}
		}
		throw new IllegalArgumentException("[ERROR] 유효한 상품 이름을 입력하세요.");
	}

	public boolean isFinished(int inputCoin) {
		if (getMinimumPriceProduct() > inputCoin) {
			return true;
		}
		if (isNoProducts()) {
			return true;
		}
		return false;
	}

	public int getMinimumPriceProduct() {
		int minPrice = this.products.get(0).getPrice();
		for (Product p: products) {
			if (minPrice > p.getPrice()) {
				minPrice = p.getPrice();
			}
		}
		return minPrice;
	}

	public boolean isNoProducts() {
		for (Product p: products) {
			if (p.getCount() != 0) {
				return false;
			}
		}
		return true;
	}
}
