package Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Player {
	@SerializedName("PlayerId")
	private String PlayerId = "";
	@SerializedName("Name")
	private String Name = "";
	@SerializedName("Username")
	private String Username = "";
	@SerializedName("Password")
	private String Password = "";
	@SerializedName("Email")
	private String Email = "";
	@SerializedName("Birthdate")
	private Date Birthdate = null;
	@SerializedName("Gender")
	private boolean Gender = true;
	@SerializedName("Wins")
	private int Wins = 0;
	@SerializedName("Loses")
	private int Loses = 0;
	@SerializedName("MaxWinSequence")
	private int MaxWinSequence = 0;
	@SerializedName("MaxLoseSequence")
	private int MaxLoseSequence = 0;
	@SerializedName("CurrentWinSequence")
	private int CurrentWinSequence = 0;
	@SerializedName("CurrentLoseSequence")
	private int CurrentLoseSequence = 0;
	@SerializedName("IQPoint")
	private int IQPoint = 0;
	@SerializedName("Image")
	private String Image = "";
	@SerializedName("Status")
	private int Status = 0;
	// in game status
	private int PlayerStatus = 0;

	private static Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy")
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	public Player() {
	}

	// create player in db
	public Player(String name, String username, String password, String email, String birthdate, boolean gender) {
		PlayerId = "";
		this.copyProfile(name, username, password, email, birthdate, gender);
	}

	// player with birth date is Date
	public Player(String playerId, String name, String username, String password, String email, Date birthdate,
			boolean gender) {
		PlayerId = playerId;
		this.copyProfile(name, username, password, email, birthdate, gender);
	}

	// player with birth date is String
	public Player(String playerId, String name, String username, String password, String email, String birthdate,
			boolean gender) {
		PlayerId = playerId;
		this.copyProfile(name, username, password, email, birthdate, gender);
	}

	// full player
	public Player(String playerId, String name, String username, String password, String email, Date birthdate,
			boolean gender, int wins, int loses, int maxWinSequence, int maxLoseSequence, int currentWinSequence,
			int currentLoseSequence, int iQPoint, String image, int status) {
		PlayerId = playerId;
		this.copyProfile(name, username, password, email, birthdate, gender);
		this.copyGame(wins, loses, maxWinSequence, maxLoseSequence, currentWinSequence, currentLoseSequence, iQPoint,
				image, status);
	}

	public Player(String playerId, String name, String username, String password, String email, String birthdate,
			boolean gender, int wins, int loses, int maxWinSequence, int maxLoseSequence, int currentWinSequence,
			int currentLoseSequence, int iQPoint, String image, int status) {
		PlayerId = playerId;
		this.copyProfile(name, username, password, email, birthdate, gender);
		this.copyGame(wins, loses, maxWinSequence, maxLoseSequence, currentWinSequence, currentLoseSequence, iQPoint,
				image, status);
	}

	public Player(Player p) {
		this.copy(p);
	}

	private void copy(Player p) {
		PlayerId = p.PlayerId;
		Name = p.Name;
		Username = p.Username;
		Password = p.Password;
		Email = p.Email;
		Birthdate = p.Birthdate;
		Gender = p.Gender;
		Wins = p.Wins;
		Loses = p.Loses;
		MaxWinSequence = p.MaxWinSequence;
		MaxLoseSequence = p.MaxLoseSequence;
		CurrentWinSequence = p.CurrentWinSequence;
		CurrentLoseSequence = p.CurrentLoseSequence;
		IQPoint = p.IQPoint;
		Image = p.Image;
		Status = p.Status;
	}

	private void copyGame(int wins, int loses, int maxWinSequence, int maxLoseSequence, int currentWinSequence,
			int currentLoseSequence, int iQPoint, String image, int status) {
		Wins = wins;
		Loses = loses;
		MaxWinSequence = maxWinSequence;
		MaxLoseSequence = maxLoseSequence;
		CurrentWinSequence = currentWinSequence;
		CurrentLoseSequence = currentLoseSequence;
		IQPoint = iQPoint;
		Image = image;
		Status = status;
	}

	private void copyProfile(String name, String username, String password, String email, String birthdate,
			boolean gender) {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = ft.parse(birthdate);
			Name = name;
			Username = username;
			Password = password;
			Email = email;
			Birthdate = date;
			Gender = gender;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void copyProfile(String name, String username, String password, String email, Date birthdate,
			boolean gender) {
		Name = name;
		Username = username;
		Password = password;
		Email = email;
		Birthdate = birthdate;
		Gender = gender;
	}

	public String getId() {
		return PlayerId;
	}

	public void setId(String id) {
		PlayerId = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getBirthdate() {
		return Birthdate;
	}

	public String getBirthdateString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(this.Birthdate);
	}

	public void setBirthdate(Date birthdate) {
		Birthdate = birthdate;
	}

	public void setBirthdate(String birthdate) {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Birthdate = ft.parse(birthdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public boolean getGender() {
		return Gender;
	}

	public String getGenderString() {
		if (this.Gender == false)
			return "Nam";
		else
			return "Nữ";
	}

	public int getGenderInt() {
		if (this.Gender == false)
			return 0;
		else
			return 1;
	}

	public void setGender(boolean gender) {
		Gender = gender;
	}

	public void setGender(String gender) {
		if (gender.equals("Nam"))
			Gender = false;
		else if (gender.equals("Nữ"))
			Gender = true;
	}

	public int getWins() {
		return Wins;
	}

	public void setWins(int wins) {
		Wins = wins;
	}

	public int getLoses() {
		return Loses;
	}

	public void setLoses(int loses) {
		Loses = loses;
	}

	public int getIQPoint() {
		return IQPoint;
	}

	public void setIQPoint(int iQPoint) {
		IQPoint = iQPoint;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getMaxWinSequence() {
		return MaxWinSequence;
	}

	public void setMaxWinSequence(int maxWinSequence) {
		MaxWinSequence = maxWinSequence;
	}

	public int getMaxLoseSequence() {
		return MaxLoseSequence;
	}

	public void setMaxLoseSequence(int maxLoseSequence) {
		MaxLoseSequence = maxLoseSequence;
	}

	public int getCurrentWinSequence() {
		return CurrentWinSequence;
	}

	public void setCurrentWinSequence(int currentWinSequence) {
		CurrentWinSequence = currentWinSequence;
	}

	public int getCurrentLoseSequence() {
		return CurrentLoseSequence;
	}

	public void setCurrentLoseSequence(int currentLoseSequence) {
		CurrentLoseSequence = currentLoseSequence;
	}

	public int getPlayerStatus() {
		return PlayerStatus;
	}

	public void setPlayerStatus(int playerStatus) {
		PlayerStatus = playerStatus;
	}

	@Override
	public String toString() {
		return "Player [PlayerId=" + PlayerId + ", Name=" + Name + ", Username=" + Username + ", Password=" + Password
				+ ", Email=" + Email + ", Birthdate=" + Birthdate + ", Gender=" + Gender + ", Wins=" + Wins + ", Loses="
				+ Loses + ", MaxWinSequence=" + MaxWinSequence + ", MaxLoseSequence=" + MaxLoseSequence
				+ ", CurrentWinSequence=" + CurrentWinSequence + ", CurrentLoseSequence=" + CurrentLoseSequence
				+ ", IQPoint=" + IQPoint + ", Image=" + Image + ", Status=" + Status + "]";
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public Player ToObject(String json) {
		Player p = gson.fromJson(json, Player.class);
		this.copy(p);
		return p;
	}

}
