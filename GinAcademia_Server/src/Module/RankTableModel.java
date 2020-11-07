package Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Model.Player;
import javax.swing.table.AbstractTableModel;
 
@SuppressWarnings("serial")
public class RankTableModel extends AbstractTableModel {
    private static final int COLUMN_NO      = 0;
    private static final int COLUMN_NAME    = 1;
    private static final int COLUMN_WIN     = 2;
    private static final int COLUMN_LOSE    = 3;
    private static final int COLUMN_RATE    = 4;
    private static final int COLUMN_MAXWIN    = 5;
    private static final int COLUMN_MAXLOSE   = 6;
     
    private String[] columnNames = {"Hạng", "Họ và tên", "Thắng", "Thua","Tỉ lệ","Chuỗi thắng","Chuỗi thua"};
    private ArrayList<Player> listPlayer;
    private ArrayList<Integer> rank = new ArrayList<Integer>();
     
    public RankTableModel(ArrayList<Player> listPlayer) {
        this.listPlayer = listPlayer;
//        this.
        for (int i= 1 ; i<= this.getRowCount();++i) {
            rank.add(i);
        }
        Collections.sort(listPlayer,new RankComparator());
    }
 
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount() {
        return listPlayer.size();
    }
     
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
     
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listPlayer.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player = listPlayer.get(rowIndex);
        Object returnValue = null;
         
        switch (columnIndex) {
        case COLUMN_NO:
            returnValue = rank.get(rowIndex); //player.getId();
            break;
        case COLUMN_NAME:
            returnValue = player.getName();
            break;
        case COLUMN_WIN:
            returnValue = player.getWins();
            break;
        case COLUMN_LOSE:
            returnValue = player.getLoses();
            break;
        case COLUMN_RATE:
        	int total = player.getWins() + player.getLoses();
        	if(total == 0) returnValue = 0;
        	else returnValue =Double.valueOf(1.0*player.getWins()/(total));
            break;
        case COLUMN_MAXWIN:
            returnValue = player.getMaxWinSequence();
            break;
        case COLUMN_MAXLOSE:
            returnValue = player.getMaxLoseSequence();
            break;
        default:
            throw new IllegalArgumentException("Invalid column index");
        }
         
        return returnValue;
    }
     
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
//        Player player = listPlayer.get(rowIndex);
        if (columnIndex == COLUMN_NO) {
//        	player.setId(value.toString());
        	rank.set(rowIndex,Integer.parseInt(value.toString()));
        }      
    }
    
    class RankComparator implements Comparator<Object>
    {
		@Override
		public int compare(Object arg0, Object arg1) {
			Player p1 = (Player) arg0;
			Player p2 = (Player) arg1;
			return (p2.getWins()-p2.getLoses()) - (p1.getWins()-p1.getLoses());
		}
    }
    public int getRankById(String id) {
    	int rank = 0;
    	int n = this.getRowCount();
    	for(int i=0;i<n;++i) {
    		if(this.listPlayer.get(i).getId().equals(id)) {   			
    			rank = i+1;
    			break;
    		}
    	}
    	return rank;
    }
 
}

