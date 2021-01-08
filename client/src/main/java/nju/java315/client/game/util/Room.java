package nju.java315.client.game.util;

public class Room {
    public enum ROOM_STATE{
        ONE_PLAYER(0),TWO_PLAYER(1),PLAYING(2);
        public static final int ONT_PLAYER_VALUE = 0;
        public static final int TWO_PLAYER_VALUE = 1;
        public static final int PLAYING_VALUE = 2;

        private final int value;
        private ROOM_STATE(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    };
}
