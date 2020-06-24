package com.greenacademy.restaurantmgt.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

	private Long orderNum;

	private CustomerInfo customerInfo;

	private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
	

	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	
	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}
	private CartLineInfo findLineById(Long id) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getProductInfo().getMasp().equals(id)) {
				return line;
			}
		}
		return null;
	}

	
	public void addProduct(ProductInfo productInfo, Integer quantity) {
		CartLineInfo line = this.findLineById(productInfo.getMasp());
		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(0);
			line.setProductInfo(productInfo);
			this.cartLines.add(line);
		}
		int newQuantity = line.getQuantity() + quantity;
		if (newQuantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	
	public void updateProduct(Long id, int quantity) {
		CartLineInfo line = this.findLineById(id);
		if (line != null) {
			if (quantity <= 0) {
				this.cartLines.remove(line);
			} else {
				line.setQuantity(quantity);
			}
		}
	}

	
	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineById(productInfo.getMasp());
		if (line != null) {
			this.cartLines.remove(line);
		}
	}

	
	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}

	
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}

	
	public double getAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartLines) {
			total += line.getAmount();
		}
		return total;
	}

	
	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for (CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getMasp(), line.getQuantity());
			}
		}
	}
}
