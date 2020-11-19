package Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GameConfig implements Serializable  {
	private int NumQuestion = 5;
	private int Point = 100;
	private int Time = 10000; // milis second
	private int numPlayer = 2;
	
	public GameConfig() {
	}
	
	public GameConfig(int numQuestion, int point, int time, int numPlayer) {
		NumQuestion = numQuestion;
		Point = point;
		Time = time;
		this.numPlayer = numPlayer;
	}

	public int getNumQuestion() {
		return NumQuestion;
	}
	public void setNumQuestion(int numQuestion) {
		NumQuestion = numQuestion;
	}
	public int getPoint() {
		return Point;
	}
	public void setPoint(int point) {
		Point = point;
	}
	public int getTime() {
		return Time;
	}
	public void setTime(int time) {
		Time = time;
	}
	public int getNumPlayer() {
		return numPlayer;
	}
	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}
	
}
