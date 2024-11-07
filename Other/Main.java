
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Main {
    public static void main(String[] args) {


        JLabel group = new JLabel();
        group.setText("<html>GROUP 29 <br> PROJECT PHASE 2  </html>");
        group.setForeground(Color.BLACK);
        group.setFont(new Font("Cambria",Font.BOLD,30));
        group.setBounds(400, 0, 300, 100);
        Border border = BorderFactory.createLineBorder(Color.black,2);
        //group.setBorder(border);
        

        JLabel UMBig = new JLabel();
        ImageIcon umlogo = new ImageIcon(new ImageIcon("umlogo1.png").getImage().getScaledInstance(270,90,Image.SCALE_SMOOTH));
        UMBig.setIcon(umlogo);
        UMBig.setBounds(0,0,300,100);
        UMBig.setBorder(border);

        JPanel GroupPanel = new JPanel();
        GroupPanel.setBackground(Color.white);  //0xABB2B9
        GroupPanel.setBounds(0,0,1000,100);
        GroupPanel.setBorder(border);
        GroupPanel.setLayout(null);
        GroupPanel.add(group);

        JLabel graph = new JLabel();
        graph.setText("<html>WELCOME TO OUR GAME BASED ON GRAPH COLORING! <br> CLICK ON NEXT TO CONTINUE </html>");
        graph.setForeground(Color.white);
        graph.setFont(new Font("Cambria",Font.BOLD,20));
        graph.setBounds(150, 0, 800, 100);

        JPanel temp = new JPanel();
        temp.setBackground(new Color(0x124556)); //0x124556)
        temp.setBounds(0,100,1000,680);
        temp.setLayout(null);
        temp.setBorder(border);
        temp.add(graph);

        GUI Program = new GUI();
        
        Program.add(UMBig);
        Program.add(GroupPanel);
        Program.add(temp);

        //GroupPanel.add(group); 
        
    }

}
