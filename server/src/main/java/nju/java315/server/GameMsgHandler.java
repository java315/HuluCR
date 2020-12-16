/*
 * @Author: zb-nju
 * @Date: 2020-12-13 23:41:23
 * @LastEditors: zb-nju
 * @LastEditTime: 2020-12-16 15:08:36
 */
package nju.java315.server;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import nju.java315.server.msg.GameMsgProtocol;
import io.netty.util.CharsetUtil;

public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandler.class);

    static private HashMap<Integer, ChannelGroup> channelGroups = new HashMap<>();
    static private HashMap<Channel, Integer> channelToRoomId = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(ctx == null)
            return;

        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(ctx == null || msg == null)
            return;

        try{
            Channel channel = ctx.channel();
            if(msg instanceof GameMsgProtocol.WhatRoomsCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserEntryCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserReadyCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserPutCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserDieCmd){

            }
            else if(msg instanceof GameMsgProtocol.UserLeaveCmd){

            }
            


        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }


    }

}
