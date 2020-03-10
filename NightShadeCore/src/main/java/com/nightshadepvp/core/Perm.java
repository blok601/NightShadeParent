package com.nightshadepvp.core;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified
{
	BASECOMMAND,
	VERSION,
	EXEMPT,
	UNEXEMPT,
	UPDATE,
	CHECK,
	ADD,
	REMOVE,
	SET,
	SURVIVAL,
	S,
	CREATIVE,
	C,
	SPECTATOR,
	SP,
	SPEC,
	ADVENTURE,
	A,
	STORM,
	RAIN,
	CLEAR,
	SUN,
	REQUEST,
	DELETE;

	
	private final String id;
	
	Perm()
	{
		this.id = PermissionUtil.createPermissionId(Core.get(), this);
	}
	
	@Override
	public String getId()
	{
		return this.id;
	}
	
	public boolean has(Permissible permissible, boolean verboose)
	{
		return PermissionUtil.hasPermission(permissible, this, verboose);
	}
	
	public boolean has(Permissible permissible)
	{
		return PermissionUtil.hasPermission(permissible, this);
	}
}
