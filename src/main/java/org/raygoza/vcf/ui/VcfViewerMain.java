package org.raygoza.vcf.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;

import org.raygoza.vcf.interval.IntervalTree;
import org.raygoza.vcf.io.VcfReader;
import org.raygoza.vcf.model.VcfEntry;
import org.raygoza.vcf.model.VcfModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author raygoza
 */
public class VcfViewerMain extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public VcfViewerMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
       
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);
        fc= new JFileChooser();
        jButton1.setText("Gff");
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);
        setTitle("Vcf Viewer");
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null,null},
               
            },
            new String [] {
                "Chromosome", "Pos", "Ref", "Alt","Type"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        me = this;
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				int retval =fc.showOpenDialog(me);
				if(retval == JFileChooser.APPROVE_OPTION) {
					IntervalTree<String> tree = new IntervalTree<String>();
					BufferedReader rd = new BufferedReader(new FileReader(fc.getSelectedFile()));
					String line ="";
					
					while(true) {
						line=rd.readLine();
						if(line==null) break;
						if(line.startsWith("#")) continue;
						String[] values = line.split("\t");
						
						if(values[2].equals("gene")) {
							Properties props = new Properties();
							props.load(new StringReader(values[8].replace(";", "\n")));
							tree.addInterval(Long.parseLong(values[3]), Long.parseLong(values[4]), props.getProperty("locus_tag"));
							
							
						}
						
					}
					
					for(int i=0; i < jTable1.getRowCount();i++) {
						List<String> genes = tree.get(Long.parseLong(jTable1.getModel().getValueAt(i, 1).toString()));
						System.out.println(Long.parseLong(jTable1.getModel().getValueAt(i, 1).toString()));
						if(genes.size()>0) {
							System.out.println(genes.get(0));
							jTable1.getModel().setValueAt(genes.get(0), i, 5);
							
							//String description = getFeatureDescription(genes.get(0));
							
						}
					}
					
					
					
					
				}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
        
        createMenuBar(jMenuBar1);
        setJMenuBar(jMenuBar1);
        
        setSize(400,500);
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    
    public String getFeatureDescription(String feature) {
    	
    	return "";
    	
    	
    	
    }
    
    
    public void createMenuBar(javax.swing.JMenuBar menuBar) {
    	
    	
    	JMenu file = new JMenu("File");
    	
    	JMenuItem open = new JMenuItem("Open...");
    	open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				int retval =fc.showOpenDialog(me);
				if(retval == JFileChooser.APPROVE_OPTION) {
					VcfReader reader = new VcfReader();
					model = reader.readModel(fc.getSelectedFile().getAbsolutePath());
					
					updateUI(model);
					
				}
				}catch(Exception ex) {
					
				}
			}
		});
    	
    	JMenuItem quit = new JMenuItem("Quit");
    	quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
    	file.add(open);
    	file.add(quit);
    	menuBar.add(file);
    }
    
    
    private void updateUI(VcfModel model) {
    	
    	Vector<String> chroms = model.getChromosomes(); 
    	
    	DefaultTableModel tmodel = new DefaultTableModel(new Object[] {"Chromosome","Pos","Ref","Alt","Type","Feature","Feature Description"},0);
    	
    	for(String chrom: chroms) {
    		
    		Vector<VcfEntry> entries = model.getEntries(chrom);
    		
    		for(VcfEntry entry: entries) {
    			String type="";
    			if(isSNP(entry)) {
    				type="SNP";
    			}
    			tmodel.addRow(new Object[] {chrom,Long.valueOf(entry.getStart()),entry.getRef(),entry.getAlt(),type,"",""});
    			
    			
    		}
    		
    		
    	}
    	jTable1.setModel(tmodel);
    	
    }
    
    private boolean isSNP(VcfEntry entry) {
    	
    	if(entry.getAlt().length()==1 && entry.getRef().length()==1 && entry.getAlt().toUpperCase().matches("[A|C|G|T]") && entry.getRef().toUpperCase().matches("[A|C|G|T]")) return true;
    	
    		return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VcfViewerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VcfViewerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VcfViewerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VcfViewerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VcfViewerMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser fc;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JFrame me;
    VcfModel model=null;
    // End of variables declaration                   
}
