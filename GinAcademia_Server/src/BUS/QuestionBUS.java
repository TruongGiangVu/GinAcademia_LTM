package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import DAO.QuestionDAO;
import Model.Option;
import Model.Question;

public class QuestionBUS {
	private QuestionDAO dao = new QuestionDAO();

	public ArrayList<Question> ReadAll() {
		return dao.readAll();
	}

	public ArrayList<Question> Read() {
		return dao.read();
	}

	public ArrayList<Question> ReadContest(int numQuestion) {
		ArrayList<Question> arr = dao.read();
		Collections.shuffle(arr);
		ArrayList<Question> ans = new ArrayList<Question>();
		for (int i = 0; i < numQuestion; ++i) {
			ans.add(arr.get(i));
		}
		return ans;
	}

	public void insert(Question p) {
		dao.insert(p);
	}

	public void delete(String id) {
		dao.delete(id);
	}

	public void block(String id) {
		dao.disable(id);
	}

	public void active(String id) {
		dao.active(id);
	}

	public void update(Question p) {
		dao.update(p);
	}

	public void updateOption(String questionid, ArrayList<Option> arr) {
		Question p = dao.getQuestionById(questionid);
		p.setOptions(arr);
		dao.update(p);
	}

	public ArrayList<Option> getOptionByQuestionId(String id) {
		Question p = dao.getQuestionById(id);
		return p.getOptions();
	}

	public Question getQuestionById(String id) {
		return dao.getQuestionById(id);
	}

	public ArrayList<Question> sort(ArrayList<Question> arr, boolean isAscending) {
		if (!isAscending)
			Collections.sort(arr, Comparators.ID);
		return arr;
	}

	public ArrayList<Question> search(int status) {
		ArrayList<Question> arr = dao.readAll();
		return this.search(arr, status);
	}

	public ArrayList<Question> search(ArrayList<Question> arr, int status) {
		ArrayList<Question> ans = new ArrayList<Question>();
		for (Question p : arr) {
			if (p.getStatus() == status)
				ans.add(p);
		}
		return ans;
	}

	public ArrayList<Question> searchQuestion(ArrayList<Question> arr, String question) {
		question = this.normalizeStr(question);
		question = question.toLowerCase();
		ArrayList<Question> ans = new ArrayList<Question>();
		for (Question p : arr) {
			if (p.getQuestion().indexOf(question) >= 0)
				ans.add(p);
		}
		return ans;
	}
	
	public int getSize() {
		return dao.readAll().size();
	}

	public static class Comparators {

		public static Comparator<Question> ID = new Comparator<Question>() {
			@Override
			public int compare(Question o1, Question o2) {
				return o2.getId().compareTo(o1.getId());
			}
		};
	}

	public String normalizeStr(String str) {
		str = str.toLowerCase();
		str = str.trim();
		str = str.replaceAll("\\s+", " ");
		return str;
	}

}
