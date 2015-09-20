package com.mybookingpal.server.api.junit;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.shared.Image;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

public class ImageMapperTest {
	
	SqlSession sqlSession;
	
	@Before
	public void setup(){
		sqlSession = RazorServer.openSession();
	}

	@Test
	public void testGet() {
		Image image = sqlSession.getMapper(ImageMapper.class).read("1");
		System.out.println("Image: " + image.getName());
	}
	
	@Test
	public void testCreate() {
		Image image = new Image();
		image.setLanguage("EN");
		image.setName("Test234.jpg");
		image.setUrl("http://s3.mybookingpal.com");
//		image.setType(Image.Type.Linked);
		image.setProductId(2345);
		sqlSession.getMapper(ImageMapper.class).create(image);
		sqlSession.commit();
	}

}
