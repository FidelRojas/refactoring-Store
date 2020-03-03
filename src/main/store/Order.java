package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) {
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public float total() {
		float totalAmountOfAllItems = 0;
		for (OrderItem item : items) {
			float totalAmountOfAnItem=0;
			float itemAmount = item.getProduct().getUnitPrice() * item.getQuantity();
			totalAmountOfAnItem = calculateAmountOfAccesories(item, totalAmountOfAnItem, itemAmount);
			totalAmountOfAnItem = calculateAmountOfBikes(item, totalAmountOfAnItem, itemAmount);
			totalAmountOfAnItem = calculateAmountOfCloathing(item, totalAmountOfAnItem, itemAmount);
			totalAmountOfAllItems += totalAmountOfAnItem;
		}

		if (this.deliveryCountry == "USA"){
			// total=totalItems + tax + 0 shipping
			return totalAmountOfAllItems + totalAmountOfAllItems * 5 / 100;
		}

		// total=totalItemst + tax + 15 shipping
		return totalAmountOfAllItems + totalAmountOfAllItems * 5 / 100 + 15;
	}

	private float calculateAmountOfCloathing(OrderItem item, float totalAmountOfAnItem, float itemAmount) {
		if (isCloathing(item)) {
			float cloathingDiscount = 0;
			if (item.getQuantity() > 2) {
				cloathingDiscount = item.getProduct().getUnitPrice();
			}
			totalAmountOfAnItem = itemAmount - cloathingDiscount;
		}
		return totalAmountOfAnItem;
	}

	private boolean isCloathing(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Cloathing;
	}

	private float calculateAmountOfBikes(OrderItem item, float totalAmountOfAnItem, float itemAmount) {
		if (isBikes(item)) {
			// 20% discount for Bikes
			totalAmountOfAnItem = itemAmount - itemAmount * 20 / 100;
		}
		return totalAmountOfAnItem;
	}

	private boolean isBikes(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private float calculateAmountOfAccesories(OrderItem item, float totalAmountOfAnItem, float itemAmount) {
		if (isAccessories(item)) {
			float booksDiscount = 0;
			if (itemAmount >= 100) {
				booksDiscount = itemAmount * 10 / 100;
			}
			totalAmountOfAnItem = itemAmount - booksDiscount;
		}
		return totalAmountOfAnItem;
	}

	private boolean isAccessories(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}
}
