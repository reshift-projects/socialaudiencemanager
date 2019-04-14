/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.til.service.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.til.service.common.dao.ArticleDao;
import com.til.service.common.dao.TopicPageArticleDao;
import com.til.service.common.dao.TopicPageDao;
import com.til.service.common.dao.WebsiteDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:test-servlet-context.xml","classpath:test-root-context.xml" })
public class TwitterWebApiTest {

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Autowired
	private TopicPageDao topicPageDao;
	
	@Autowired
	private WebsiteDao websiteDao;
	
	@Autowired
	private ArticleDao	articleDao;
	
	@Autowired
	private TopicPageArticleDao	topicPageArticleDao;
	
	@Autowired
	private MessageSource messageSource;
	
    @BeforeClass
    public static void setupProperties() {
        System.setProperty("catalina.base", "C:\\sam-tomcat-7.0.42");
    }
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void testTwitterWebPost(){
    	try
    	{
	    	//MediaType mediaType = new MediaType("application/json;charset=UTF-8");
	    	String message = "Sunanda Pushkar and Shashi Tharoor had a bitter spat on Jan 15";
	    	String url = "http://timesofindia.indiatimes.com/india/Sunanda-Pushkar-and-Shashi-Tharoor-had-a-bitter-spat-on-Jan-15/articleshow/29032409.cms";
	        this.mockMvc.perform(post("/api/twitter/v1/post/87739697")
	        		.param("url", url).param("message", message).param("appcode", "toi"))
	        		.andExpect(status().isOk())
	        		//.andExpect(content().contentType("application/json"))
	        		.andExpect(jsonPath("$.code").value("200"));
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	// Test for rest url
	// http://localhost:8080/sam-webapp/api/twitter/v1/post/87739697

	// @Test
	/*public void testTwitterWebPost() throws Exception {
		String targetURL = "http://localhost:8080/sam-webapp/api/twitter/v1/post/87739697";
		HttpURLConnection connection = createUrlConnection(targetURL);
		connection.setRequestMethod("POST");
		StringBuilder urlParameters = new StringBuilder();
		urlParameters.append("url=http://timesofindia.indiatimes.com/assembly-elections-2013/delhi-assembly-elections/Arvind-Kejriwal-says-AAP-wont-support-BJP/articleshow/27174393.cms&");
		urlParameters.append("message=Test Twitter API&");
		urlParameters.append("appcode=toi");
		// urlParameters.append("&scheduleddate=1387180672200");
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters.toString());
		int responseCode = connection.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Response from API : " + response.toString());
	}*/

	//@Test
	public void testTwitterWebImagePost() throws Exception {
		PostMethod filePost = new PostMethod(
				"http://localhost:8080/sam-webapp/api/twitter/v1/postimage/87739697");
		HttpClient client = new HttpClient();
		File file = new File("D:/arvind.jpg");
		String url = "http://timesofindia.indiatimes.com/india/Cabinet-nod-for-probe-into-Gujarats-snooping-scandal/articleshow/27964554.cms";
		String message = "Cabinet nod for probe into Gujarat's snooping scandal";
		Part[] parts = { new StringPart("url", url),
				new StringPart("message", message),
				new StringPart("appcode", "TOI"),
				new FilePart("fileupload", file) };
		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost
				.getParams()));

		int status = client.executeMethod(filePost);
		System.out.println("Status is " + status);
	}

	public static HttpURLConnection createUrlConnection(String url)
			throws Exception {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
		} catch (MalformedURLException murle) {
			throw new Exception("A malformed URL: " + url, murle);
		} catch (IOException ioe) {
			throw new Exception("Input/Output error with Connection ", ioe);
		}
		return connection;
	}
}
