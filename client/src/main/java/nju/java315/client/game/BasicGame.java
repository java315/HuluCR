/*
 * @Author: zb-nju
 * @Date: 2020-12-13 23:48:37
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-14 00:15:24
 */
package nju.java315.client.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class BasicGame extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        System.out.println();
    }

}
