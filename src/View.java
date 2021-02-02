import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class View{
    public static void main(String[] args) throws Exception {
        Frame f = new Frame();
        f.setSize(500,820);

        Panel panel1 = new Panel();
        panel1.setSize(500,600);

        Panel panel2 = new Panel();
        panel2.setSize(500,200);



        MenuBar m = new MenuBar();
        f.setMenuBar( m );

        Menu m_menu = new Menu("Menu");
        m.add(m_menu);

        MenuItem i_load = new MenuItem("Load");
        m_menu.add(i_load);

        MenuItem i_run = new MenuItem("Run");
        m_menu.add(i_run);



        TextArea t = new TextArea("",35,60,TextArea.SCROLLBARS_VERTICAL_ONLY);
        t.setSize(500,600);

        panel1.add(t);

        TextArea out = new TextArea("",0,60,TextArea.SCROLLBARS_VERTICAL_ONLY);
        out.setEditable(false);
        out.setSize(500,200);

        panel2.add(out);

        f.add(panel1,"North");
        f.add(panel2,"South");


        f.setVisible(true);

        i_run.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("run button");
                out.setText("runrun");
            }
        });


        i_load.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String temps = new String();
                String temps2 = new String();

                FileDialog dialog = new FileDialog(f, "Load", FileDialog.LOAD);
                dialog.setFile("*.txt;");
                dialog.setVisible(true);

                String path = dialog.getDirectory() + dialog.getFile();

                if( dialog.getFile() == null ) return;



                try{
                    FileReader filereader = new FileReader(path);
                    int singleCh = 0;
                    while((singleCh = filereader.read()) != -1){
                        temps = temps.concat((char)singleCh+"");
                    }
                    filereader.close();
                }catch (FileNotFoundException e2) {
                    // TODO: handle exception
                }catch(IOException e2){
                    System.out.println(e2);
                }


                t.setText(temps);
            }
        });
    }
}