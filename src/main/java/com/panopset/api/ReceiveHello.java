package com.panopset.api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.panopset.example.Hello;
import com.panopset.example.HelloService;

public final class ReceiveHello {

	static final String NEW_WSDL_PATH = "/home/pan/hdd/temp/Hello.wsdl";
	static final String[] REPLACE = new String[] {
			"http://foo/WebServiceProject/services/Hello",
			"http://localhost:8080/WebServiceProject/services/Hello" };

	public static void main(String[] args) throws Exception {
		new ReceiveHello().go();
	}

	private File file;

	private File getFile() {
		if (file == null) {
			file = new File(NEW_WSDL_PATH);
			if (!file.exists() || file.length() == 0) {
				new TextResourceToFile("com/panopset/api/Hello.wsdl") {

					@Override
					protected String transform(String s) {
						return s.replace(REPLACE[0], REPLACE[1]);
					}
				}.write(file);
			}
		}
		return file;
	}

	private void go() throws MalformedURLException, URISyntaxException {
		QName serviceName = new QName("http://example.panopset.com",
				"HelloService");
		Service shs = HelloService.create(getFile().toURI().toURL(),
				serviceName);
		System.out.println(shs.getPort(Hello.class).sayHello());
	}
}
