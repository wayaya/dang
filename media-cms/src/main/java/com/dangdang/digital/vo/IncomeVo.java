package com.dangdang.digital.vo;
/**
 * Description: 用于数据统计页面显示，收入
 * All Rights Reserved.
 * @version 1.0  2015年3月30日 下午7:48:22  by yangzhenping（yangzhenping@dangdang.com）创建
 */
public class IncomeVo {
	/**
	 * 送包月人数
	 */
	private Long userCountSend;
	/**
	 * 购买包月人数
	 */
	private Long userCountBuyMonthly;
	/**
	 * 购买包月次数
	 */
	private Long timesBuyMonthly;
	/**
	 * 购买包月金额（金铃铛）
	 */
	private Long totalBuyMonthly;
	/**
	 * 打赏人数
	 */
	private Long userCountReward;
	/**
	 * 打赏次数
	 */
	private Long timesReward;
	/**
	 * 打赏金额（金铃铛）
	 */
	private Long totalReward;
	/**
	 * 充值人数
	 */
	private Long userCountDeposit;
	/**
	 * 充值次数
	 */
	private Long timesDeposit;
	/**
	 * 充值总金额
	 */
	private Long totalDeposit;
	/**
	 * 单章购买人数
	 */
	private Long userCountBuyChapter;
	/**
	 * 单章购买次数
	 */
	private Long timesBuyChapter;
	/**
	 * 单张购买金铃铛总额
	 */
	private Long totalGoldBuyChapter;
	/**
	 * 单张购买银铃铛总额
	 */
	private Long totalSilverBuyChapter;
	/**
	 * 购买福袋人数
	 */
	private Long userCountBuyLuckyBag;
	/**
	 * 购买福袋次数
	 */
	private Long timesBuyLuckyBag;
	/**
	 * 购买福袋总金额（金币）
	 */
	private Long totalCoinBuyLuckyBag;
	/**
	 * 福袋抽奖人数
	 */
	private Long userCountLottery;
	/**
	 * 福袋抽奖次数
	 */
	private Long timesLottery;
	/**
	 * 福袋抽奖总银铃铛
	 */
	private Long totalSilverLottery;
	/**
	 * 膜拜总人数
	 */
	private Long userCountWorship;
	/**
	 * 膜拜次数
	 */
	private Long timesWorship;
	/**
	 * 膜拜总银铃铛
	 */
	private Long totalSilverWorship;
	/**
	 * 领取掉钱带（撒金币）人数
	 */
	private Long userCountSpreadCoins;
	/**
	 * 领取掉钱带（撒金币）次数
	 */
	private Long timesSpreadCoins;
	/**
	 * 领取掉钱带（撒金币）银铃铛总数
	 */
	private Long totalSilverSpreadCoins;
	public Long getUserCountSend() {
		return userCountSend;
	}
	public void setUserCountSend(Long userCountSend) {
		this.userCountSend = userCountSend;
	}
	public Long getUserCountBuyMonthly() {
		return userCountBuyMonthly;
	}
	public void setUserCountBuyMonthly(Long userCountBuyMonthly) {
		this.userCountBuyMonthly = userCountBuyMonthly;
	}
	public Long getTimesBuyMonthly() {
		return timesBuyMonthly;
	}
	public void setTimesBuyMonthly(Long timesBuyMonthly) {
		this.timesBuyMonthly = timesBuyMonthly;
	}
	public Long getTotalBuyMonthly() {
		return totalBuyMonthly;
	}
	public void setTotalBuyMonthly(Long totalBuyMonthly) {
		this.totalBuyMonthly = totalBuyMonthly;
	}
	public Long getUserCountReward() {
		return userCountReward;
	}
	public void setUserCountReward(Long userCountReward) {
		this.userCountReward = userCountReward;
	}
	public Long getTimesReward() {
		return timesReward;
	}
	public void setTimesReward(Long timesReward) {
		this.timesReward = timesReward;
	}
	public Long getTotalReward() {
		return totalReward;
	}
	public void setTotalReward(Long totalReward) {
		this.totalReward = totalReward;
	}
	public Long getUserCountDeposit() {
		return userCountDeposit;
	}
	public void setUserCountDeposit(Long userCountDeposit) {
		this.userCountDeposit = userCountDeposit;
	}
	public Long getTimesDeposit() {
		return timesDeposit;
	}
	public void setTimesDeposit(Long timesDeposit) {
		this.timesDeposit = timesDeposit;
	}
	public Long getTotalDeposit() {
		return totalDeposit;
	}
	public void setTotalDeposit(Long totalDeposit) {
		this.totalDeposit = totalDeposit;
	}
	public Long getUserCountBuyChapter() {
		return userCountBuyChapter;
	}
	public void setUserCountBuyChapter(Long userCountBuyChapter) {
		this.userCountBuyChapter = userCountBuyChapter;
	}
	public Long getTimesBuyChapter() {
		return timesBuyChapter;
	}
	public void setTimesBuyChapter(Long timesBuyChapter) {
		this.timesBuyChapter = timesBuyChapter;
	}
	public Long getTotalGoldBuyChapter() {
		return totalGoldBuyChapter;
	}
	public void setTotalGoldBuyChapter(Long totalGoldBuyChapter) {
		this.totalGoldBuyChapter = totalGoldBuyChapter;
	}
	public Long getTotalSilverBuyChapter() {
		return totalSilverBuyChapter;
	}
	public void setTotalSilverBuyChapter(Long totalSilverBuyChapter) {
		this.totalSilverBuyChapter = totalSilverBuyChapter;
	}
	public Long getUserCountBuyLuckyBag() {
		return userCountBuyLuckyBag;
	}
	public void setUserCountBuyLuckyBag(Long userCountBuyLuckyBag) {
		this.userCountBuyLuckyBag = userCountBuyLuckyBag;
	}
	public Long getTimesBuyLuckyBag() {
		return timesBuyLuckyBag;
	}
	public void setTimesBuyLuckyBag(Long timesBuyLuckyBag) {
		this.timesBuyLuckyBag = timesBuyLuckyBag;
	}
	public Long getTotalCoinBuyLuckyBag() {
		return totalCoinBuyLuckyBag;
	}
	public void setTotalCoinBuyLuckyBag(Long totalCoinBuyLuckyBag) {
		this.totalCoinBuyLuckyBag = totalCoinBuyLuckyBag;
	}
	public Long getUserCountLottery() {
		return userCountLottery;
	}
	public void setUserCountLottery(Long userCountLottery) {
		this.userCountLottery = userCountLottery;
	}
	public Long getTimesLottery() {
		return timesLottery;
	}
	public void setTimesLottery(Long timesLottery) {
		this.timesLottery = timesLottery;
	}
	public Long getTotalSilverLottery() {
		return totalSilverLottery;
	}
	public void setTotalSilverLottery(Long totalSilverLottery) {
		this.totalSilverLottery = totalSilverLottery;
	}
	public Long getUserCountWorship() {
		return userCountWorship;
	}
	public void setUserCountWorship(Long userCountWorship) {
		this.userCountWorship = userCountWorship;
	}
	public Long getTimesWorship() {
		return timesWorship;
	}
	public void setTimesWorship(Long timesWorship) {
		this.timesWorship = timesWorship;
	}
	public Long getTotalSilverWorship() {
		return totalSilverWorship;
	}
	public void setTotalSilverWorship(Long totalSilverWorship) {
		this.totalSilverWorship = totalSilverWorship;
	}
	public Long getUserCountSpreadCoins() {
		return userCountSpreadCoins;
	}
	public void setUserCountSpreadCoins(Long userCountSpreadCoins) {
		this.userCountSpreadCoins = userCountSpreadCoins;
	}
	public Long getTimesSpreadCoins() {
		return timesSpreadCoins;
	}
	public void setTimesSpreadCoins(Long timesSpreadCoins) {
		this.timesSpreadCoins = timesSpreadCoins;
	}
	public Long getTotalSilverSpreadCoins() {
		return totalSilverSpreadCoins;
	}
	public void setTotalSilverSpreadCoins(Long totalSilverSpreadCoins) {
		this.totalSilverSpreadCoins = totalSilverSpreadCoins;
	}
	
}
