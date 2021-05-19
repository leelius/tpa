package app.test;

import org.apache.commons.codec.digest.DigestUtils;

public class TestMd5 {

	public static void main(String[] args) {
		String username = "2014110559";

		String md5digest = DigestUtils.md5Hex(username);

		System.out.println("TestMd5.main(username)=" + md5digest);

	}

}
