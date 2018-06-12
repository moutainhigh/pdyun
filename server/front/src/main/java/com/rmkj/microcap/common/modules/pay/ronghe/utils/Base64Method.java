package com.rmkj.microcap.common.modules.pay.ronghe.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Method {

	public static String EncryptBase64(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(a_strString
				.getBytes("utf-8")), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_');
		return base64str;
	}

	public static String EncryptBase64(byte[] bytes) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(bytes), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_');
		return base64str;
	}

	public static String DecryptBase64(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	}

	public static byte[] DecryptBase64ForByte(String a_strString)
			throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		return bytes;
	}

	public static void main(String[] args) throws Exception {
		String str = "ts_cgfppWa7kNdsrkpboiEJzk3HxyyfmYLK01MqQJmbqimRrfWJWeBJHLlXLIf73ijjjUh4E1vgXN_WkAymIAmD25EJsfEnbgRH06S5205SLitge_PunjIztt7V0DR_jISk2Uj63zg6fq1Jnmv9uU6GXdfLUnPcrtH5j3shcpth7uHLxIMS0yAPDbZKIDv7y";
		System.out.println(AesEncryption.Desencrypt(str, "rdPNKSNyK6L91wIx", "rdPNKSNyK6L91wIx"));
	}
}
