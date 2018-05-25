import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Manager implements ActionListener, ClipboardOwner {
     private JPanel pa = new JPanel(new BorderLayout());
     private JTextField homeTextField = new JTextField("C:\\");
     private JTable jt = new JTable();
     private String[] com = {"한글", "English"}; //콤보박스 2가지 영역 설정
     private JComboBox comboBox1 = new JComboBox(com);
     private JList<String> list1 = new JList<>();
     private JScrollPane sp = new JScrollPane();
     private JScrollPane spl = new JScrollPane();
     private JLabel jl = new JLabel("File Manager");
     private JPanel jc = new JPanel(new BorderLayout());
     private JPanel ju = new JPanel(new BorderLayout());
     private File name; 
     private File getBack; 
     private String path = "C:\\"; 
     private String[][] data; 
     private JMenuItem[] PopItem_Korea = new JMenuItem[6];
     private JMenuItem[] PopItem_English = new JMenuItem[6]; 
     private DefaultTableModel model; 
     private int[] copy;
     private String[] Korean_columnNames = {"이름", "크기", "수정한 날짜"}; 
     private String[] English_columnNames = {"Name", "Size", "Modified"}; 
     private Vector<String> copyFile; 
     private String[] directoryName_List; 
 
 
     public Manager() {
    	 JFrame f = new JFrame("파일 매니저");
         f.setLayout(new BorderLayout());
         PopItem_Korea[0] = new JMenuItem("폴더에 항목 표시");
         PopItem_Korea[1] = new JMenuItem("복사하기"); 
         PopItem_Korea[2] = new JMenuItem("붙여넣기"); 
         PopItem_Korea[3] = new JMenuItem("삭제"); 
         PopItem_English[0] = new JMenuItem("Show Item in the Folder"); 
         PopItem_English[1] = new JMenuItem("Copy"); 
         PopItem_English[2] = new JMenuItem("Paste"); 
         PopItem_English[3] = new JMenuItem("Delete"); 
         jt.setBackground(Color.white);
         jt.getTableHeader().setReorderingAllowed(false); 
         sp.setPreferredSize(new Dimension(200, -1)); 
         homeTextField.setText("C:\\"); //경로 이름 지정
         PopItem_Korea[2].setEnabled(false); //붙여넣기 영역 비활성화(한글)
         PopItem_English[2].setEnabled(false); //Paste 영역(영어)
         getJList();

         list1.addMouseListener(new MouseAdapter() { 
             @Override 
             public void mouseClicked(MouseEvent e) { 
                 try { 
                     String clicked; 
                     getBack = new File(path, ".."); 
                     clicked = list1.getSelectedValue(); 
                     if (clicked.equals("..")) { 
                         try {
                            path = getBack.getCanonicalPath(); 
                         } catch(Exception ignored) {} 
                         homeTextField.setText(path); 
                         getJList(); 
                     } else { 
                         path = name.getPath() + File.separator + clicked; 
                         if (path.contains("C:\\\\")) 
                             path = name.getPath() + clicked;
                         homeTextField.setText(path);
                         getJList();
                         } 
                 } catch(NullPointerException aee) {} 
             }
         }); 
         spl.addMouseListener(new MouseAdapter() { 
             @Override 
             public void mouseClicked(MouseEvent e) { 
                if (SwingUtilities.isRightMouseButton(e)) { 
                     JPopupMenu PopMenu = new JPopupMenu(); 
                     if (comboBox1.getSelectedItem() == "한글") { 
                         for (int i = 0; i < 4; i++) { 
                             if (i == 1) PopMenu.addSeparator(); 
                             if (i == 1 || i == 3) continue; 
                             PopMenu.add(PopItem_Korea[i]); 
                        } 
                     } else { 
                         for (int i = 0; i < 4; i++) { 
                             if (i == 1) PopMenu.addSeparator(); 
                            if (i == 1 || i == 3) continue; 
                             PopMenu.add(PopItem_English[i]); 
                         } 
                     } 
                     PopMenu.show(jt, e.getX(), e.getY()); 
                     PopMenu.setVisible(true); 
                 } 
             } 
         }); 
 
 
         jt.addMouseListener(new MouseAdapter() { 
             @Override 
             public void mouseClicked(MouseEvent e) { 
             	//다시해보자 
             	 
             	if(e.getClickCount()==2) { 
             		Desktop desk = Desktop.getDesktop(); 
             		int getcount = jt.getSelectedRow(); 
             		String open_File = path + File.separator + jt.getValueAt(getcount, 0); 
             		File open = new File(open_File); 
            		try { 
 						desk.open(open); 
 					} catch (IOException e1) { 
						if (comboBox1.getSelectedItem() == "한글") 
                             JOptionPane.showMessageDialog(null, "열 수 없습니다.", "에러", JOptionPane.ERROR_MESSAGE); 
                         else 
                             JOptionPane.showMessageDialog(null, "you can't open.", "Error", JOptionPane.ERROR_MESSAGE); 
					} 
             	} 
             	if (SwingUtilities.isRightMouseButton(e)) { 
                     JPopupMenu PopMenu = new JPopupMenu(); 
                     if (comboBox1.getSelectedItem() == "한글") { 
                         for (int i = 0; i < 4; i++) { 
                             if (i == 1 || i == 3) PopMenu.addSeparator(); 
                             PopMenu.add(PopItem_Korea[i]); 
                         } 
                     } else { 
                         for (int i = 0; i < 4; i++) { 
                             if (i == 1 || i == 3) PopMenu.addSeparator(); 
                             PopMenu.add(PopItem_English[i]); 
                         } 
                     } 
                     PopMenu.show(jt, e.getX(), e.getY()); 
                     PopMenu.setVisible(true); 
                 } 
             } 
         }); 
         for (int i = 0; i < 4; i++) { 
             PopItem_English[i].addActionListener(this);
             PopItem_Korea[i].addActionListener(this);
         }
         sp.setViewportView(list1);
         spl.setViewportView(jt);
         jc.add(jl, BorderLayout.WEST); 
         jc.add(comboBox1, BorderLayout.EAST); 
         ju.add(spl, BorderLayout.CENTER); 
         ju.add(sp, BorderLayout.WEST); 
         pa.add(homeTextField, BorderLayout.NORTH); 
         pa.add(jc, BorderLayout.SOUTH); 
         pa.add(ju, BorderLayout.CENTER); 
         
         f.setSize(750, 600); 
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
         f.add(pa); 
         f.setVisible(true); 
          
         comboBox1.addActionListener(e -> setTable()); 
    } 
 
 
     @Override 
     public void lostOwnership(Clipboard aClipboard, Transferable aContents) { 
         //do nothing 
     } 
 
 
     public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == PopItem_Korea[3] || e.getSource() == PopItem_English[3]) { 
             int[] columns = jt.getSelectedRows(); 
             for (int column : columns) { 
                 System.out.println(column); 
                 System.out.println(path + File.separator + jt.getValueAt(column, 0)); 
             } 
             for (int column : columns) { 
                 File delete = new File(path + File.separator + jt.getValueAt(column, 0)); 
                 delete.delete(); 
             } 
             for (int i = 0; i < columns.length; i++) 
                 model.removeRow(columns[i] - i); 
             model.fireTableDataChanged(); 
             jt.updateUI(); 
         } 
 
 
         if (e.getSource() == PopItem_Korea[0] || e.getSource() == PopItem_English[0]) { 
             File open_Directory = new File(path); 
             try { 
                 Desktop.getDesktop().open(open_Directory); 
             } catch (IOException ignored) { 
 
 
             } 
         } 
 
 
         if (e.getSource() == PopItem_Korea[1] || e.getSource() == PopItem_English[1]) { 
             PopItem_Korea[2].setEnabled(true); 
             PopItem_English[2].setEnabled(true); 
             copy = jt.getSelectedRows(); 
             copyFile = new Vector<>(jt.getRowCount()); 
             for (int i = 0; i < copy.length; i++) { 
                copyFile.add(i, (path + "\\" + jt.getValueAt(copy[i], 0))); 
             } 
        } 
 
 
         if (e.getSource() == PopItem_Korea[2] || e.getSource() == PopItem_English[2]) { 
            String tmp = path; 
             int count = 0; 
             for (int i = 0; i < copy.length; i++) { 
                 String command = "cmd /c copy \"" + copyFile.get(i) + "\"" + " \"" + tmp + "\" /y"; 
               try { 
                     Process child = Runtime.getRuntime().exec(command); 
                     InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949"); 
                     int c; 
                    StringBuilder result; 
                     result = new StringBuilder(); 
                     while ((c = in.read()) != -1) { 
                         result.append((char) c); 
                     } 
                    if (result.toString().contains("0개 파일이 복사되었습니다.")) { 
                         if (comboBox1.getSelectedItem() == "한글") 
                             JOptionPane.showMessageDialog(null, "액세스 권한이 없습니다.", "에러", JOptionPane.ERROR_MESSAGE); 
                         else 
                             JOptionPane.showMessageDialog(null, "You don't have access", "Error", JOptionPane.ERROR_MESSAGE); 
                         in.close(); 
                         break; 
                     } 
                     in.close(); 
                     count++; 
                 } catch (Exception ee) { 
                     ee.printStackTrace(); 
                 } 
             } 
            getJList(); 
            if (count == copy.length) { 
                 homeTextField.setText(path); 
                 getJList(); 
             } 
         } 
     } 

     //리스트 배치
     private void getJList() {  
         File[] directory_list;
         File[] file_list;
         name = new File(path);

         directory_list = name.listFiles(File::isDirectory); 
         file_list = name.listFiles(File::isFile); 
 
 
         //파일 불러오기
         int count = 1; 
         directoryName_List = new String[0]; 
         if (directory_list != null) { 
             directoryName_List = new String[directory_list.length + 1]; 
             for (int i = -1; i < directory_list.length; i++) { 
                 String back = ".."; 
                 if (i == -1) directoryName_List[0] = back; 
                 else { 
                     if (directory_list[i].getName().contains("$") || 
                             directory_list[i].getName().contains("Recovery") || 
                             directory_list[i].getName().contains("System") || 
                             directory_list[i].getName().contains("Temp") || 
                             directory_list[i].getName().contains("PerfLogs") || 
                             directory_list[i].getName().contains("Documents and Settings") || 
                             !directory_list[i].canRead()) continue; 
 
 
                     directoryName_List[count] = directory_list[i].getName(); 
                     count++; 
                 }
             }
         }
 
 
         list1.setListData(directoryName_List); 
         if (list1.getVisibleRowCount() != 0) 
             data = new String[0][3]; 
         if (file_list != null) { 
             { 
                 data = new String[file_list.length][3]; 
                 for (int i = 0; i < file_list.length; i++) { 
                     data[i][0] = file_list[i].getName(); 
                     String file_size; 
                     long size = file_list[i].length();
                     if (size < 1024) { 
                    	 file_size = String.format("%d B", size); 
                    	 } else if (size < 1024 * 1024) {
                        	 file_size = String.format("%.2f KB", size / 1024.0); 
                        	 } else if (size < 1024 * 1024 * 1024) {
                        		 file_size = String.format("%.2f MB", size / 1048576.0); 
                        		 } else { 
                        			 file_size = String.format("%.2f GB", size / 1073741824.0); 
                        			 }
                     data[i][1] = file_size;
                     Date dt = new Date(file_list[i].lastModified());
                     SimpleDateFormat formatter = new SimpleDateFormat("d/M/y HH:mm:ss"); //일/월/년도 시:분:초 
                     String date = formatter.format(dt); 
                     data[i][2] = String.valueOf(date); 
                } 
             } 
             
             setTable(); 
         } else {
             
             getBack = new File(path, ".."); 
             try { 
                 path = getBack.getCanonicalPath(); 
             } catch (Exception ignored) {} 
             homeTextField.setText(path); 
             getJList();
         } 
     }
     
     private void setTable() { 
         if (comboBox1.getSelectedItem() == "한글") { 
             model = new DefaultTableModel(data, Korean_columnNames) { 
            	public boolean isCellEditable(int rowIndex, int mColIndex) { 
             		return false; 
             	}
             	 
             }; 
             jt.setModel(model); 
             jl.setText("파일 탐색기"); 
         }
         if(comboBox1.getSelectedItem() == "English") { 
             model = new DefaultTableModel(data, English_columnNames) { 
             	public boolean isCellEditable(int rowIndex, int mColIndex) { 
             		return false; 
             	}	 
             }; 
             jt.setModel(model); 
             jl.setText("File Explorer");
         } 
     }
     public static void main(String[] args) { 
    	 new Manager(); 
     }
}