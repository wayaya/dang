package com.dangdang.digital.service.nearby.impl.nearby.bean;


import com.dangdang.digital.service.nearby.impl.nearby.util.Pair;

/**
 * 
 * Description: 树节点
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2014年7月21日 下午6:17:25  by 林永奇（linyongqi@dangdang.com）创建
 */
public class DefaultNode<T> implements INode<T> {

    /**
     * 标识
     */
    private int nodeFlag = 0;

    private INode<T> leftChild;


    /**
     * 右子节点
     */
    private INode<T> rightChild;


    private boolean isLeaf = false;

    /**
     * 该节点的区间
     */
    private Pair<Double, Double> nodeRange;

    /**
     * 该节点的数据
     */
    private T nodeData;


    private int nodeLevel = 0;

    @Override
    public int getNodeLevel() {
        return nodeLevel;
    }

    @Override
    public void setNodeLevel(int level) {
        this.nodeLevel = level;
    }

    @Override
    public void setLeftChild(INode<T> node) {
        this.leftChild = node;
    }

    @Override
    public void setRightChild(INode<T> node) {
        this.rightChild = node;
    }

    @Override
    public void setNodeData(T data) {
        this.nodeData = data;
    }

    @Override
    public boolean isLeaf() {
        return null == this.leftChild && null == this.rightChild;
    }

    @Override
    public void setNodeFlag(int flag) {
        this.nodeFlag = flag;
    }

    @Override
    public void setNodeRange(Pair<Double, Double> pair) {
        this.nodeRange = pair;
    }


    @Override
    public INode<T> getLeftChild() {
        return this.leftChild;
    }

    @Override
    public INode<T> getRightChild() {
        return this.rightChild;
    }

    @Override
    public T getNodeData() {
        return this.nodeData;
    }


    @Override
    public int getNodeFlag() {
        return this.nodeFlag;
    }

    @Override
    public Pair<Double, Double> getNodeRange() {
        return this.nodeRange;
    }

    @Override
    public boolean isInclude(double value) {
        return this.nodeRange.left <= value && value < this.nodeRange.right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultNode that = (DefaultNode) o;

        if (isLeaf != that.isLeaf) return false;
        if (nodeFlag != that.nodeFlag) return false;
        if (leftChild != null ? !leftChild.equals(that.leftChild) : that.leftChild != null) return false;
        if (nodeData != null ? !nodeData.equals(that.nodeData) : that.nodeData != null) return false;
        if (rightChild != null ? !rightChild.equals(that.rightChild) : that.rightChild != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeFlag;
        result = 31 * result + (leftChild != null ? leftChild.hashCode() : 0);
        result = 31 * result + (rightChild != null ? rightChild.hashCode() : 0);
        result = 31 * result + (isLeaf ? 1 : 0);
        result = 31 * result + (nodeData != null ? nodeData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultNode{");
        sb.append("nodeFlag=").append(nodeFlag);
        sb.append(", isLeaf=").append(isLeaf);
        sb.append(", nodeRange=").append(nodeRange.left).append(",").append(nodeRange.right);
        sb.append('}');
        return sb.toString();
    }
}
