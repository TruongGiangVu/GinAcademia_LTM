package Server.BUS;

import java.util.ArrayList;

import Server.DAO.IQTestDAO;
import Model.Option;
import Model.IQTest;

public class IQTestBUS {
	private IQTestDAO dao = new IQTestDAO();

	public ArrayList<IQTest> ReadAll() {
		return dao.readAll();
	}
	public ArrayList<IQTest> Read() {
		return dao.readAll();
	}
	public void insert(IQTest p) {
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

	public void update(IQTest p) {
		dao.update(p);
	}
	public void updateOption(String questionid, ArrayList<Option> arr) {
		IQTest p = dao.getIQTestById(questionid);
		p.setOptions(arr);
		dao.update(p);
	}
	
	public ArrayList<Option> getOptionByIQTestId(String id) {
		IQTest p = dao.getIQTestById(id);
		return p.getOptions();
	}

}
