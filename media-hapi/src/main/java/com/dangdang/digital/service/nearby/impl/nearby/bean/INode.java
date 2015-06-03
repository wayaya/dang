package com.dangdang.digital.service.nearby.impl.nearby.bean;


import com.dangdang.digital.service.nearby.impl.nearby.util.Pair;


public interface INode<T> {


    /**
     *设置这个节点的区间
     * @param pair
     */
    public void setNodeRange(Pair<Double, Double> pair);


    /**
     * 获取该节点的区间
     * @return
     */
    public Pair<Double,Double> getNodeRange();


    /**
     * 获取节点的层级
     */
    public int getNodeLevel();


    public void setNodeLevel(int level);


    /**
     * 是否在当前节点里
     * @param value
     * @return
     */
    public boolean isInclude(double value);

    /**
     * 设置左子节点
     *
     * @param node
     */
    public void setLeftChild(INode<T> node);


    /**
     * 获取左子节点
     * @return
     */
    public INode<T> getLeftChild();


    /**
     * 设置右子节点
     *
     * @param node
     */
    public void setRightChild(INode<T> node);


    /**
     * 获取右子节点
     * @return
     */
    public INode<T> getRightChild();

    /**
     * 设置节点的数据
     *
     * @param data
     */
    public void setNodeData(T data);

    /**
     * 设置节点数据
     * @return
     */
    public T getNodeData();



    /**
     * 是否为叶子节点
     *
     * @return
     */
    public boolean isLeaf();


    /**
     * 设置该节点的标识
     * @param flag
     */
    public void setNodeFlag(int flag);

    /**
     * 获取该节点的标识
     * @return
     */
    public int getNodeFlag();




}
