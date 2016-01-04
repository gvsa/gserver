package org.mlin.id;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.mlin.dbtools.DBTools;

import com.mongodb.client.FindIterable;

public class UID {
	private static String value;

	public static String createUser(String arg[]) throws Exception {
		String res = "";
		String pid = null, cid = null, name = null;
		for (int i = 0; i < arg.length; i += 2) {
			if (arg[i].equals("pid")) {
				pid = arg[i + 1];
			} else if (arg[i].equals("cid")) {
				cid = arg[i + 1];
			}
			if (arg[i].equals("name")) {
				name = arg[i + 1];
			}
		}

		if (UID.userExists(pid)) {
			return "attempt to create duplicate user!:" + pid;
		}
		DBTools.getMongoDB().getCollection("blocklist").insertOne(new Document().append("pid", pid).append("cid", cid).append("name", name));

		if (UID.userExists(pid)) {
			res = "User created successfully!";
		} else {
			res = "User creation failed!\nkindly send an email with the following key:" + pid;
		}
		return res;
	}

	private static boolean userExists(String pid) throws Exception {
		boolean res = false;

		try {
			FindIterable<Document> clist = DBTools.getMongoDB().getCollection("blocklist").find();
			for (Document d : clist) {
				if (d.get("pid").equals(pid)) {
					res = true;
					break;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return res;
	}

	public static boolean isUnique(String idVal) throws Exception {
		boolean ret = false;
		try {
			FindIterable<Document> itList = DBTools.getMongoDocs("blocklist");
			List<String> idList = new ArrayList<String>();
			for (Document d : itList) {
				idList.add((String) d.get("pid"));
			}
			ret = true;
			if (idList.contains(idVal)) {
				ret = false;
			}
		} catch (Exception e) {
			return false;
		}
		return ret;
	}
}
