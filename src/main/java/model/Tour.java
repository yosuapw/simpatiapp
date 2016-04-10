package model;

public class Tour {
	private String headTitle;
	private String image;
	private String additionalInfo;
	private Price prices;

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Price getPrices() {
		return prices;
	}

	public void setPrices(Price prices) {
		this.prices = prices;
	}

}
