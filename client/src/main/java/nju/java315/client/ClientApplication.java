/*
 * @Author: zb-nju
 * @Date: 2020-12-13 23:41:23
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-14 00:18:53
 */
package nju.java315.client;

import com.almasb.fxgl.app.GameApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nju.java315.client.game.HuluCRApp;


@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);

		System.out.println("launch game");
		GameApplication.launch(HuluCRApp.class,args);
	}

}
