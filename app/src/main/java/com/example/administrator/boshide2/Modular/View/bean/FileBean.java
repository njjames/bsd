package com.example.administrator.boshide2.Modular.View.bean;


import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeId;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodeLabel;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeNodePid;

public class FileBean
{


	@TreeNodeId(type = String.class)
	private String _id;
	@TreeNodePid(type = String.class)
	private String parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String desc;

	public FileBean(String _id, String parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
