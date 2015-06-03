package com.dangdang.digital.service.nearby.impl.nearby.bean;

import com.dangdang.digital.service.nearby.impl.nearby.util.Base32Util;
import com.dangdang.digital.service.nearby.impl.nearby.util.Pair;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * Description: 经纬度裂变树 All Rights Reserved.
 * 
 * @param <T>
 * @version 1.0 2014年7月21日 下午6:17:06 by 林永奇（linyongqi@dangdang.com）创建
 */
public class BlockTree<T> implements ITree {

	private static org.slf4j.Logger LOG = LoggerFactory
			.getLogger(BlockTree.class);

	/**
	 * 维度的树
	 */
	private INode<T> latitudeTree;

	/**
	 * 经度的树
	 */
	private INode<T> longtitudeTree;

	private static final String PREFIX_GEO_HASH = "GeoHash";

	/**
	 * 树的深度
	 */
	private int treeDepth = 10;

	/**
	 * 纬度的有效位数 即层级深度
	 */
	private int latBitNum = 10;

	/**
	 * 经度的有效位数 即深度
	 */
	private int lngBitNum = 10;

	@Override
	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	@Override
	public int getTreeDepth() {
		return this.treeDepth;
	}

	public BlockTree() {
	}

	public BlockTree(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	@Override
	public void init() {

		int totalBits = Base32Util.genValidBitNum(treeDepth);

		this.latBitNum = totalBits / 2;

		// 开始分割数据
		this.latitudeTree = splitAndBuildTree(this.latBitNum, -90d, 90d);

		LOG.info("初始化分裂纬度树完毕...|bits:{0}", this.latBitNum);

		this.lngBitNum = (totalBits - totalBits / 2);

		// 开始分裂经度数据
		this.longtitudeTree = splitAndBuildTree(this.lngBitNum, -180d, 180);
		LOG.info("初始化分裂经度树完毕...|{0}", this.lngBitNum);

	}

	@Override
	public List<String> expandGeoHash(String geohash, int level) {
		return Base32Util.expandGeoHash(geohash, this.treeDepth,
				this.lngBitNum, this.latBitNum, level);
	}

	@Override
	public List<String> expandGeoHashSquare(String geohash, int level) {
		return Base32Util.expandGeoHashSquare(geohash, this.treeDepth,
				this.lngBitNum, this.latBitNum, level); // To change body of
														// implemented methods
														// use File | Settings |
														// File Templates.
	}

	@Override
	public Map<Integer, List<String>> sortGeoHashByDistance(String geohash,
			List<String> list) {
		return Base32Util.sortByDistanceLevel(geohash, this.treeDepth, list);
	}

	@Override
	public String encodeGeoHash(String businessType, double lat, double lng)
			throws Exception {

		// 搜索经度对应的标识路径
		long lngPath = searchGeoPath(lng, this.longtitudeTree);

		// 搜索纬度标识路径
		long latPath = searchGeoPath(lat, this.latitudeTree);

		// 合并最后的结果
		/**
		 * 因为定义的的层级 所以 latpath 和lngpath的位数是{@code BlockTree#treeDepth}
		 */
		long flag = Base32Util.composeLatLngBits(lngPath, latPath,
				this.lngBitNum, this.latBitNum);
		String geoHash = Base32Util.encode(flag, this.treeDepth);
		return this.marshalGeoHash(businessType, geoHash);
	}

	@Override
	public String unmarshalGeoHash(String businessType, String geohash) {
		String prefix = PREFIX_GEO_HASH + "_" + businessType + ":";
		if (geohash.indexOf(prefix) != 0) {
			throw new IllegalArgumentException(
					"非法的geocode:格式为GeoHash_$businessType:$geocode+|prefix:"
							+ prefix);
		}
		return geohash.substring(geohash.lastIndexOf(":") + 1);
	}

	@Override
	public String marshalGeoHash(String businessType, String geohash) {
		return PREFIX_GEO_HASH + "_" + businessType + ":" + geohash;
	}

	@Override
	public Pair<Double, Double> locateCentralPoint(String businessType,
			String geocode) {

		/**
		 * 先解码
		 */
		geocode = this.unmarshalGeoHash(businessType, geocode);
		long[] lngLat = Base32Util.decomposeLatLng(geocode, this.treeDepth);

		INode<T> lngNode = this.searchTreeNode(this.longtitudeTree, lngLat[0],
				this.lngBitNum);
		INode<T> latNode = this.searchTreeNode(this.latitudeTree, lngLat[1],
				this.latBitNum);

		/**
		 * 计算中心节点
		 */
		double midLng = (lngNode.getNodeRange().left + lngNode.getNodeRange().right) / 2;
		double midLat = (latNode.getNodeRange().left + latNode.getNodeRange().right) / 2;

		// 返回中心节点
		return new Pair<Double, Double>(midLat, midLng);
	}

	/**
	 * 搜索对应的
	 *
	 * @param flag
	 * @param nodeCursor
	 * @return
	 */
	INode<T> searchTreeNode(INode<T> nodeCursor, long flag, int bitNum) {

		StringBuilder sb = null;
		if (LOG.isDebugEnabled()) {
			sb = new StringBuilder();
			sb.append(flag).append("|");
		}

		INode<T> currNode = nodeCursor;
		int bit = 0;
		while (null != currNode) {
			/**
			 * 由高位到低位获得当前位的状态 决定走左子树还是右子树
			 */
			bit = (int) (flag >> (bitNum - currNode.getNodeLevel())) & 0x01;
			/**
			 * 判断是
			 */
			if (0 == bit) {
				currNode = currNode.getLeftChild();
			} else {
				currNode = currNode.getRightChild();
			}
			if (LOG.isDebugEnabled()) {
				sb.append(currNode.getNodeFlag() + "|"
						+ currNode.getNodeRange().left + ","
						+ currNode.getNodeRange().right + "\n");
			}
			/**
			 * 如果判断是一个叶子节点，则返回
			 */
			if (currNode.isLeaf()) {
				break;
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("查找最终的分块|{0}|{1}", flag, sb.toString());
		}
		return currNode;

	}

	/**
	 * 通过给定的树的根节点和给定的value 来确定属于哪个树的
	 *
	 * @param value
	 * @param nodeCursor
	 */
	int searchGeoPath(double value, INode<T> nodeCursor) {

		StringBuilder sb = null;
		if (LOG.isDebugEnabled()) {
			sb = new StringBuilder(this.treeDepth);
		}
		int flag = 0;
		INode<T> parent = nodeCursor;
		/**
		 * 当前节点不为叶子节点继续向下查找
		 */
		while (null != nodeCursor) {
			parent = nodeCursor;
			INode<T> leftChild = nodeCursor.getLeftChild();

			if (leftChild.isInclude(value)) {
				// 如果是叶子节点则直接返回，否则继续向下查找
				nodeCursor = leftChild;
			} else {

				INode<T> rightChild = nodeCursor.getRightChild();
				/**
				 * 如果右子节点包括在里面则继续按照右子结点向下搜索
				 */
				if (rightChild.isInclude(value)) {
					nodeCursor = rightChild;
				} else {
					/**
					 * 既没有包含在左子节点又没有包含在右子节点，那证明你妹的有BUG 直接置当前遍历的节点为null退出既可以
					 */
					nodeCursor = null;
					LOG.warn("INVALID SEARCH PATH |{0}|{1}", value, rightChild);
					break;
				}
			}

			flag = ((flag << 1) | (nodeCursor.getNodeFlag()));
			// 如果到了叶子节点，则跳出循环
			if (null == nodeCursor || nodeCursor.isLeaf()) {
				break;
			}

			if (LOG.isDebugEnabled()) {
				sb.append("\n[").append(nodeCursor.getNodeRange().left)
						.append(",").append(nodeCursor.getNodeRange().right)
						.append("]:").append(nodeCursor.getNodeFlag());
			}

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("搜索到属于自己的叶子节点|{0}|{1}", value, parent + "|" + flag + "|"
					+ sb.toString());
		}

		return flag;
	}

	/**
	 * 分裂并构建树
	 *
	 * @param treeDepth
	 * @param firstVal
	 * @param secondVal
	 * @return
	 */
	private INode<T> splitAndBuildTree(int treeDepth, double firstVal,
			double secondVal) {

		// 循环建树
		Queue<INode<T>> queue = new LinkedList<INode<T>>();
		INode<T> root = new DefaultNode<T>();
		root.setNodeFlag(1);
		root.setNodeRange(new Pair<Double, Double>(firstVal, secondVal));
		root.setNodeLevel(1);
		// 把根节点放入队列中
		queue.offer(root);

		while (!queue.isEmpty()) {
			// 从队列中poll出数据
			INode<T> parent = queue.poll();
			if (parent.getNodeLevel() > treeDepth) {
				break;
			}

			Pair<Double, Double> pair = parent.getNodeRange();

			// 取两个值的中间值
			double mid = (pair.left + pair.right) / 2;
			// 分裂成两个节点
			INode<T> leftChild = buildChild(pair.left, mid, 0x00,
					parent.getNodeLevel() + 1);
			INode<T> rightChild = buildChild(mid, pair.right, 0x01,
					parent.getNodeLevel() + 1);

			// 设置两个子节点
			parent.setLeftChild(leftChild);
			parent.setRightChild(rightChild);

			// 将两个子节点Push到队列中
			queue.offer(leftChild);
			queue.offer(rightChild);
		}

		return root;
	}

	/**
	 * 构建自己的孩子
	 *
	 * @param inclusive
	 * @param exclusive
	 * @param nodeFlag
	 * @return
	 */
	private INode<T> buildChild(double inclusive, double exclusive,
			int nodeFlag, int nodeLevel) {
		INode<T> child = new DefaultNode<T>();
		child.setNodeLevel(nodeLevel);
		child.setNodeRange(new Pair<Double, Double>(inclusive, exclusive));
		child.setNodeFlag(nodeFlag);
		return child;
	}
}
