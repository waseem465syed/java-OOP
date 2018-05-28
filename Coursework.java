package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author waseem
 */
public class Coursework extends JFrame implements ActionListener, KeyListener, TreeSelectionListener {

    JTextArea txtNewNote = new JTextArea(30, 30);
    JTextArea txtDisplayNotes = new JTextArea();
    JTree tree;
    ArrayList<String> note = new ArrayList<>();
    ArrayList<String> course = new ArrayList<>();
    JComboBox courseList = new JComboBox();
    String crse = "";
    Button bb = new Button(this);
    CommonCode cc = new CommonCode(this);
    Panel pp = new Panel(this);
    JTextField search = new JTextField();

    DefaultTreeModel model;
    String textStr[];
    DefaultMutableTreeNode root;

    String rootName = "Test";
    ArrayList<Course> courselists = new ArrayList<Course>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        JOptionPane.showMessageDialog(null, "Waseem Syed - 000989146");
        Coursework prg = new Coursework();

    }

    void writeObject() {

        Course child[] = new Course[courselists.size()];
        child = courselists.toArray(child);

        cc.writeObjectToFile(child);
    }

    ArrayList<Course> readObject() throws ClassNotFoundException {

        Course[] crc = cc.readObjectFromFile();
        if (crc != null) {

            for (Course c : crc) {
                courselists.add(c);
                System.out.println(c.getCourseName());
            }
            return courselists;
        }
        return null;
    }

    public void initializeData() throws ClassNotFoundException {

        courselists.clear();
//        
        ArrayList<Course> temp = readObject();
//        
//        
        if (temp != null) {
            courselists = temp;
        }

    }

    // Using MVC
    public Coursework() throws ClassNotFoundException {

        model();
        view();
        controller();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

//   ActionCommand to DELETE a COURSE
        if ("delcourse".equals(ae.getActionCommand())) {
            System.out.println("delete enter");
            if ((DefaultMutableTreeNode) model.getRoot() != null) {

                System.out.println("combo selection: " + courseList.getSelectedIndex());
                int currentIndex = courseList.getSelectedIndex();
                int changeIndex;
                if (currentIndex == courselists.size() - 1) {
                    changeIndex = 0;
                } else {
                    changeIndex = currentIndex;
                }
                courselists.remove(currentIndex);
                courseList.removeItemAt(currentIndex);
                courseList.setSelectedIndex(changeIndex);

            }

        }
        if ("outputtext".equals(ae.getActionCommand())) {
            try {
                String sb = cc.getDateAndTime();
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("/home/me/Documents"));
                int retrival = chooser.showSaveDialog(null);
                FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".txt");
                //BufferedWriter bw = new BufferedWriter(fw);
                fw.write(sb.toString());
                txtDisplayNotes.write(fw);
                fw.close();
                txtDisplayNotes.setText("");
                txtDisplayNotes.requestFocus();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

// ActionCommand to  MODIFY REQUIREMENTS
        if ("modifyrequirements".equals(ae.getActionCommand())) {

            printListOfRequirement(courseList.getSelectedIndex());

        }
// Action Command to Save NOTEs
        if ("SaveNote".equals(ae.getActionCommand())) {

            //get index of selected value
            int cindex = getindexOfCourse(tree.getModel().getRoot().toString());
            int noteIndex = getCurrentIndexOfTree();
            System.out.println("cindex" + cindex);
            System.out.println("noteIndex" + noteIndex);

            courselists.get(cindex).addNote(noteIndex, txtDisplayNotes.getText());

            writeObject();
            txtDisplayNotes.setText("");

        }

        // ActionCommand to Search keyword
        if ("SearchKeyword".equals(ae.getActionCommand())) {
            if (!search.getText().equals("")) {
                cc.highlight(txtDisplayNotes, search.getText());
            } else {
                cc.removeHighlights(txtDisplayNotes);
            }

        }

        // ActionCommand to CLOSE window
        if ("close".equals(ae.getActionCommand())) {
            txtNewNote.setText("");
        }

        // ActionCommand to EXIT
        if ("Exit".equals(ae.getActionCommand())) {

            writeObject();

            System.exit(0);
        }

        //ActionCommand to ADD a COURSE
        if ("addcourse".equals(ae.getActionCommand())) {

//            CommonCode
            String courseName = cc.showMessage(this, "Course Name", "Enter course Name");
            while (courseName == null) {
                courseName = cc.showMessage(this, "Course Name", "Enter course Name");

            }

            if (tree == null) {
                tree = new JTree();
            }
            Course course = new Course();
            course.setCourseName(courseName);
            courselists.add(course);
            courseList.addItem(courseName);


            showListOfRequirement(courseName);
            System.out.println(crse);
        }
    }

    int getindexOfCourse(String crs) {
        int index = 0;

        for (int i = 0; i < courselists.size(); i++) {

            if (courselists.get(i).getCourseName() == crs) {
                index = i;
            }

        }
        return index;

    }

    public void printListOfRequirement(int sindex) {

        txtDisplayNotes.setText("");
        JTextArea ta = new JTextArea(20, 20);
        int index1 = sindex;

        ArrayList<String> tlistOfRequirement = new ArrayList<>(), tnoteOfRequirment = new ArrayList<>();

        ArrayList<String> listOfRequirement = courselists.get(index1).getRequirementList();
        String temp = "";
        for (String cc : listOfRequirement) {
            temp = temp + (cc + '\n');

        }

        ta.setText(temp);

        switch (JOptionPane.showConfirmDialog(null, new JScrollPane(ta), "Please Modify your requirement list", JOptionPane.INFORMATION_MESSAGE)) {
            case JOptionPane.OK_OPTION:
                textStr = ta.getText().split("\\r\\n|\\n|\\r");
                System.out.println(ta.getText());
//                courselists.get(index).

                for (String crse : textStr) {

                    int index = sindex;
                    int requirementIndex = manageNotes(listOfRequirement, crse);
                    if (requirementIndex != -1) {

                        tlistOfRequirement.add(crse);
                        tnoteOfRequirment.add(courselists.get(index).getNote(requirementIndex));

                    } else {
                        tlistOfRequirement.add(crse);
                        tnoteOfRequirment.add("");

                    }

                }
                courselists.get(index1).setLists(tlistOfRequirement, tnoteOfRequirment);
                courseList.setSelectedIndex(index1);

                break;
        }
    }

    int manageNotes(ArrayList<String> listOfString, String crc) {
        int indexOfValue = -1;
        indexOfValue = listOfString.indexOf(crc);

        return indexOfValue;

    }

    public void showListOfRequirement(String crsName) {

        JTextArea ta = new JTextArea(20, 20);
        switch (JOptionPane.showConfirmDialog(null, new JScrollPane(ta), "Please enter Requirement list line by line", JOptionPane.INFORMATION_MESSAGE)) {
            case JOptionPane.OK_OPTION:
                textStr = ta.getText().split("\\r\\n|\\n|\\r");
                for (String crse : textStr) {

                    int index = getindexOfCourse(crsName);

                    courselists.get(index).addList(crse);

                }

                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped Not coded yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!search.getText().equals("")) {
                cc.highlight(txtDisplayNotes, search.getText());
            } else {
                cc.removeHighlights(txtDisplayNotes);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased Not coded yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void model() throws ClassNotFoundException {

        initializeData();

    }

    private void view() {
        Font fnt = new Font("Georgia", Font.PLAIN, 18);
        JButton button = null;
        JPanel panel = null;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Coursework -  Waseem Syed 000989146");

        panel = pp.makePanel();

        createTree(0);
        JScrollPane scrollBar1 = new JScrollPane(tree);
        scrollBar1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollBar1.setBackground(Color.GREEN);
        scrollBar1.setForeground(Color.YELLOW);
        txtNewNote.setLineWrap(true);
        panel.add(scrollBar1);
        add(panel, BorderLayout.WEST);

        // new MenuBar created
        JMenuBar menuBar = new JMenuBar();
        menuBar.setForeground(Color.BLUE);

        button = bb.makeButton("course1", "addcourse", "Add a course", "Add Course", fnt);
        menuBar.add(button);

        courseList.setFont(fnt);

        courseList.setMaximumSize(courseList.getPreferredSize());
        courseList.addActionListener(this);
        courseList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("check1");
                if (!courselists.isEmpty()) {
                    if ((DefaultMutableTreeNode) model.getRoot() != null) {
                        DefaultMutableTreeNode root1 = (DefaultMutableTreeNode) model.getRoot();
                        root1.removeAllChildren();
                        model.reload();
                        model.setRoot(new DefaultMutableTreeNode(courseList.getSelectedItem().toString()));
//
                        String child[] = new String[courselists.get(courseList.getSelectedIndex()).getRequirementList().size()];
                        child = courselists.get(courseList.getSelectedIndex()).getRequirementList().toArray(child);

                        addFiles(child, model, (DefaultMutableTreeNode) model.getRoot());
//                    tree.setModel(model);

                        tree.expandPath(new TreePath((DefaultMutableTreeNode) model.getRoot())); //To change body of generated methods, choose Tools | Templates.
                    }
                }
            }
        });
//        courseList.setActionCommand("Course");
        menuBar.add(courseList);

        button = bb.makeButton("Trash", "delcourse", "Delete the course", "Delete Course", fnt);
        menuBar.add(button);

        button = bb.makeButton("req", "modifyrequirements", "Modify requirements", "Modify Requirements", fnt);
        menuBar.add(button);

        button = bb.makeButton("exit1", "Exit", "Exit", "", fnt);
        menuBar.add(button);

        // menuBar added to this Frame directly with THIS command
        this.setJMenuBar(menuBar);

        //=========================================================================
        JToolBar toolBar = new JToolBar();
        toolBar.addSeparator();
        button = bb.makeButton("outputtext", "outputtext", "Export Text File .", "Export...", fnt);
        toolBar.add(button);
        toolBar.addSeparator();
        button = bb.makeButton("Save", "SaveNote", "Save the note", "Save Note", fnt);
        toolBar.add(button);
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalGlue());
        search.setMaximumSize(new Dimension(6900, 30));
        search.setFont(fnt);
        search.addKeyListener(this);
        toolBar.add(search);
        toolBar.addSeparator();
        button = bb.makeButton("search", "SearchKeyword", "Search for this text", "Search", fnt);
        toolBar.add(button);
        add(toolBar, BorderLayout.NORTH);

        panel = pp.makePanel();
        txtDisplayNotes.setFont(fnt);

        panel.add(txtDisplayNotes);
        add(panel, BorderLayout.CENTER);

        JScrollPane scrollBar = new JScrollPane(txtDisplayNotes);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.setBackground(Color.BLUE);
        scrollBar.setForeground(Color.YELLOW);
        txtDisplayNotes.setLineWrap(true);
        panel.add(scrollBar);
        setVisible(true);
        setDefaultCloseOperation(0);

    }

    public void createTree(int status) {

        if (!courselists.isEmpty()) {
            tree = new JTree();
            System.out.println("enter");
            for (Course cc : courselists) {
                courseList.addItem(cc.getCourseName());

            }
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(courselists.get(0).getCourseName());
            model = new DefaultTreeModel(root);
            String[] child = new String[courselists.get(0).getRequirementList().size()];
            child = courselists.get(0).getRequirementList().toArray(child);
//            String child[]= (String[]) courselists.get(0).getRequirementList().toArray();

            addFiles(child, model, root);
            DefaultMutableTreeNode firstLeaf = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getFirstLeaf();
//tree.setSelectionPath(new TreePath(firstLeaf.getPath()));
            tree.setSelectionPath(new TreePath(root.getPath()));
            tree.setModel(model);
            tree.setRootVisible(true);
            tree.setShowsRootHandles(true);
            tree.addTreeSelectionListener(this);

        }

    }

    public void updateTree(final String nodeToAdd) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
//      System.out.print(pnl);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
                .getRoot();
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeToAdd);
        model.insertNodeInto(child, root, root.getChildCount());
        tree.scrollPathToVisible(new TreePath(child.getPath()));

    }

    private void controller() {

    }

    private void addNote(String text) {
        note.add(txtNewNote.getText());

    }

    private void addNewNote() {
        System.out.println("addNewNotes Not coded yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showMessageDialog(Object object, String message, String title, String crse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void addFiles(String[] child1, DefaultTreeModel model, DefaultMutableTreeNode root) {
        int i = 0;
        for (String c : child1) {

            DefaultMutableTreeNode child = new DefaultMutableTreeNode(c);
            model.insertNodeInto(child, root, i);
            i++;

        }

    }

    public int getCurrentIndexOfTree() {
        Object node = tree.getLastSelectedPathComponent();
        String name;
        name = (node == null) ? "NONE" : node.toString();

        if (node != null) {
            DefaultMutableTreeNode node1;
            node1 = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
            return node1.getParent().getIndex(node1);
        }

        return -1;

    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {

        Object node = tree.getLastSelectedPathComponent();
        String name;
        name = (node == null) ? "NONE" : node.toString();

        int crIndex = getindexOfCourse(tree.getModel().getRoot().toString());
        System.out.println(crIndex);

        if (node != null) {
            DefaultMutableTreeNode node1;
            node1 = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();

            int nindex = getCurrentIndexOfTree();
            System.out.println(nindex);

            int numberOfNotes = courselists.get(crIndex).getRequirementList().size();

            String note = courselists.get(crIndex).getNote(nindex);
            if (note.isEmpty()) {
                txtDisplayNotes.setText("");
            } else {
                txtDisplayNotes.setText(note);
            }
        }

    }

}
