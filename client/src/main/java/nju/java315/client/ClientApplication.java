package nju.java315.client;

import com.almasb.fxgl.app.GameApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nju.java315.client.game.BasicGame;
import nju.java315.client.network.NettyClient;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
		System.out.println("launch game");
		GameApplication.launch(BasicGame.class,args);
		NettyClient nc = new NettyClient();
		try {
			nc.start();
		} catch (Exception e){

		} finally {

		}
		
		
	}

}
