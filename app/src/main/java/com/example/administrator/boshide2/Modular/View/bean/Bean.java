package com.example.administrator.boshide2.Modular.View.bean;


import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeId;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeLabel;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodePid;

public class Bean
{
	@TreeNodeId(type = String.class)
	private String  id;
	@TreeNodePid(type = String.class)
	private String pId;
	@TreeNodeLabel
	private String label;

	public Bean()
	{
	}

	public Bean(String  id, String  pId, String label)
	{
		this.id = id;
		this.pId = pId;
		this.label = label;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getpId()
	{
		return pId;
	}

	public void setpId(String pId)
	{
		this.pId = pId;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

}
