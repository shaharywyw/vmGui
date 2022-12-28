package vmGui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource; 

@SuppressWarnings("serial")
public class VmGui extends JPanel {
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JButton b;    
    private ArrayList<JButton> rbs = new ArrayList<JButton>();
    private TreeMap<String,String> vms = new TreeMap<String,String>();
    public VmGui() {
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
        ItemListener il = itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                AbstractButton btn = (AbstractButton) itemEvent.getSource();
                System.out.println("Selection: " + btn.getActionCommand());
            }
        };
        
      File file = new File("vms.txt");
          try {
          BufferedReader br = new BufferedReader(new FileReader(file));
          String vm;
              while ((vm = br.readLine()) != null) {
                String name = vm.split("\\s+")[0];
                String ip = vm.split("\\s+")[1];
                  vms.put(name,ip);
              }	
        } catch (Exception e1) {
          e1.printStackTrace();
        
        JButton rb;
        int y = 0, x=0;
        for(String vm: vms.keySet()) {
        	rb = new JButton(vm);
        	rb.setPreferredSize(new Dimension(150, 20));
        	c.insets = new Insets(10,10,10,10);
        	if(x%5 == 0) {
        		y++;
        		x=0;
        	}
        	c.gridx=x;
        	c.gridy=y;
			rb.addItemListener(il);
			rbs.add(rb);
			panel.add(rb,c);
			buttonGroup.add(rb);
			rb.addActionListener(e ->{
				new Thread(new VmThread(vms.get(vm))).start();
			});
			x++;
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setPreferredSize(new Dimension(900, 800));
        setLayout(new BorderLayout());
        add(scrollPane);
    }
    
    public void actionPerformed(ActionEvent e){ 
    	System.out.println(e);
    }

    private static void createAndShowGui() {
        VmGui mainPanel = new VmGui();

        JFrame frame = new JFrame("Virtual Machines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}
