/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package admin;

import conecta.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import mapeamento_poo.Usuario;

/**
 *
 * @author Mateus
 */
public class Editar_usuarios extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int qtRec = 0;
    ArrayList<Usuario> lista = new ArrayList<>();

    /**
     * Creates new form Editar_usuarios
     */
    public Editar_usuarios() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        conexao = Conexao.conector();
        inicializarTabela();
    }

    private void inicializarTabela() {
        tabelaCliente.setRowHeight(30);
        listarclientes();
        qtRecord();
    }

    private void adicionar() {
        String sql = "INSERT INTO usuarios (nome, senha, email, perfil, telefone) VALUES (?, ?, ?, ?, ?)";

        Usuario usuario = new Usuario();

        usuario.setNome(txt1.getText());
        usuario.setSenha(txt2.getText());
        usuario.setEmail(txt3.getText());
        usuario.setTelefone(txt4.getText());

        // obter o perfil selecionado no JComboBox
        String perfilSelecionado = (String) comboBoxPerfil.getSelectedItem();
        usuario.setPerfil(perfilSelecionado);

        String confirmarSenha = txt5.getText();

        if (!usuario.getSenha().equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(null, "As senhas não conferem");
            return;
        }

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuario.getNome());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getPerfil());
            pst.setString(5, usuario.getTelefone());

            if (txt1.getText().isEmpty() || txt2.getText().isEmpty() || txt3.getText().isEmpty() || txt4.getText().isEmpty() || txt5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
                } else {
                    JOptionPane.showMessageDialog(null, "Há um erro na inserção");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void alterar() {
        String sql = "update usuarios set nome = ?, senha = ?, email = ?, perfil = ?, telefone = ? where id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txt1.getText());
            pst.setString(2, txt2.getText());
            pst.setString(3, txt3.getText());
            pst.setString(4, comboBoxPerfil.getSelectedItem().toString());
            pst.setString(5, txt4.getText());
            pst.setString(6, txtIdUserold.getText());

            if (txtIdUserold.getText().isEmpty() || txt1.getText().isEmpty() || txt2.getText().isEmpty() || txt3.getText().isEmpty() || txt4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int alterado = pst.executeUpdate();
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso");
                } else {
                    JOptionPane.showMessageDialog(null, "Há um erro na alteração");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void excluir() {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUserold.getText());

            int excluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (excluir == JOptionPane.YES_OPTION) {
                int excluido = pst.executeUpdate();
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso");
                    txt1.setText(null);
                    txt2.setText(null);
                    txt3.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ArrayList<Usuario> consultaCliente() {
        String sql = "select * from usuarios";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Usuario customer = new Usuario();
                customer.setId(rs.getInt("id"));
                customer.setNome(rs.getString("nome"));
                customer.setSenha(rs.getString("senha"));
                customer.setEmail(rs.getString("email"));
                customer.setPerfil(rs.getString("perfil"));
                customer.setTelefone(rs.getString("telefone"));

                lista.add(customer);

            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Consultar Cliente" + erro);
        }

        return lista;

    }

    private void listarclientes() {
        try {

            //Customer customer = new Customer();  
            DefaultTableModel model = (DefaultTableModel) tabelaCliente.getModel();
            model.setNumRows(0);
            ArrayList<Usuario> lista = consultaCliente();

            for (int num = 0; num < lista.size(); num++) {
                model.addRow(new Object[]{
                    lista.get(num).getId(),
                    lista.get(num).getNome(),
                    lista.get(num).getSenha(),
                    lista.get(num).getEmail(),
                    lista.get(num).getPerfil(),
                    lista.get(num).getTelefone()
                });
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Listar Cliente" + erro);

        }
    }

    public void qtRecord() {
        String sql = "select count(*) quantidade from usuarios";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                qtRec = rs.getInt(1);
            }
            lblqtRegistro.setText(String.valueOf(qtRec));

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Consultar Cliente" + erro);
        }

    }

    private void selecaotabela() {
        int row = tabelaCliente.getSelectedRow();
        String table_click = (tabelaCliente.getModel().getValueAt(row, 0).toString());

        String sql = "SELECT * FROM usuarios WHERE id='" + table_click + "'";
        try {
            pst = conexao.prepareStatement(sql);
            ResultSet rs;
            rs = pst.executeQuery();
            while (rs.next()) {
                String add1 = rs.getString("id");
                txtIdUserold.setText(add1);
                String add2 = rs.getString("nome");
                txt1.setText(add2);
                String add3 = rs.getString("senha");
                txt2.setText(add3);
                txt5.setText(""); // Limpa o campo de confirmação de senha
                String add4 = rs.getString("email");
                txt3.setText(add4);
                String add5 = rs.getString("perfil");
                comboBoxPerfil.setSelectedItem(add5);
                String add6 = rs.getString("telefone");
                txt4.setText(add6);
            }
        } catch (SQLException e) {
            // Trate a exceção aqui
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCliente = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        rSTextFullRound1 = new rojeru_san.rsfield.RSTextFullRound();
        jLabel9 = new javax.swing.JLabel();
        lblqtRegistro = new javax.swing.JLabel();
        rSButtonRound5 = new rojerusan.RSButtonRound();
        rSButtonRound6 = new rojerusan.RSButtonRound();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt3 = new rojeru_san.rsfield.RSTextFullRound();
        jLabel3 = new javax.swing.JLabel();
        txt1 = new rojeru_san.rsfield.RSTextFullRound();
        jLabel5 = new javax.swing.JLabel();
        txt5 = new rojeru_san.rsfield.RSPassViewRound();
        rSButtonRound2 = new rojerusan.RSButtonRound();
        txt2 = new rojeru_san.rsfield.RSPassViewRound();
        jLabel4 = new javax.swing.JLabel();
        txt4 = new rojeru_san.rsfield.RSTextFullRound();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboBoxPerfil = new RSMaterialComponent.RSComboBox();
        rSButtonRound3 = new rojerusan.RSButtonRound();
        rSButtonRound4 = new rojerusan.RSButtonRound();
        jLabel8 = new javax.swing.JLabel();
        txtIdUserold = new rojeru_san.rsfield.RSTextFullRound();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1170, 830));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1174, 0, -1, 806));

        jPanel2.setBackground(new java.awt.Color(104, 34, 139));

        tabelaCliente.setBackground(new java.awt.Color(104, 34, 139));
        tabelaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "Email", "Senha", "Telefone", "Perfil"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaCliente);

        jLabel1.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        jLabel1.setText("Digite o nome do usuario:");

        rSTextFullRound1.setBackground(new java.awt.Color(104, 34, 139));
        rSTextFullRound1.setForeground(new java.awt.Color(0, 102, 255));
        rSTextFullRound1.setText("Mateus Zdebski");

        jLabel9.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        jLabel9.setText("Numeros de usuarios:");

        lblqtRegistro.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        lblqtRegistro.setText("1");

        rSButtonRound5.setText("Editar");
        rSButtonRound5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound5ActionPerformed(evt);
            }
        });

        rSButtonRound6.setText("Excluir");
        rSButtonRound6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(452, 452, 452)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(rSTextFullRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(rSButtonRound5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(rSButtonRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(lblqtRegistro)
                .addGap(166, 166, 166))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(rSTextFullRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblqtRegistro)
                    .addComponent(rSButtonRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(104, 34, 139));

        jLabel2.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel2.setText("Email");

        txt3.setBackground(new java.awt.Color(104, 34, 139));
        txt3.setText("Info@gmail.com");

        jLabel3.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel3.setText("Nome");

        txt1.setBackground(new java.awt.Color(104, 34, 139));
        txt1.setText("Mateus Zdebski");

        jLabel5.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel5.setText(" Confirma senha");

        txt5.setBackground(new java.awt.Color(104, 34, 139));
        txt5.setText("rSPassViewRound1");

        rSButtonRound2.setText("Registre");
        rSButtonRound2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound2ActionPerformed(evt);
            }
        });

        txt2.setBackground(new java.awt.Color(104, 34, 139));
        txt2.setText("rSPassViewRound1");

        jLabel4.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel4.setText("Senha");

        txt4.setBackground(new java.awt.Color(104, 34, 139));
        txt4.setText("+55 47 99759-8910");

        jLabel6.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel6.setText("Telefone");

        jLabel7.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel7.setText("Perfil");

        comboBoxPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "operador", "empresa" }));

        rSButtonRound3.setText("Editar");
        rSButtonRound3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound3ActionPerformed(evt);
            }
        });

        rSButtonRound4.setText("Excluir");
        rSButtonRound4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel8.setText("Id");

        txtIdUserold.setBackground(new java.awt.Color(104, 34, 139));
        txtIdUserold.setText("1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rSButtonRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(61, 61, 61)
                        .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(rSButtonRound4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(172, 172, 172)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt4, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(txt2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(comboBoxPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(119, 119, 119)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdUserold, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdUserold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBoxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSButtonRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 810));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rSButtonRound2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound2ActionPerformed
        adicionar();
    }//GEN-LAST:event_rSButtonRound2ActionPerformed

    private void rSButtonRound3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound3ActionPerformed
        alterar();
    }//GEN-LAST:event_rSButtonRound3ActionPerformed

    private void rSButtonRound4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound4ActionPerformed
        excluir();
    }//GEN-LAST:event_rSButtonRound4ActionPerformed

    private void rSButtonRound5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound5ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        selecaotabela();
    }//GEN-LAST:event_rSButtonRound5ActionPerformed

    private void rSButtonRound6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound6ActionPerformed
        excluir();
    }//GEN-LAST:event_rSButtonRound6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSComboBox comboBoxPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblqtRegistro;
    private rojerusan.RSButtonRound rSButtonRound2;
    private rojerusan.RSButtonRound rSButtonRound3;
    private rojerusan.RSButtonRound rSButtonRound4;
    private rojerusan.RSButtonRound rSButtonRound5;
    private rojerusan.RSButtonRound rSButtonRound6;
    private rojeru_san.rsfield.RSTextFullRound rSTextFullRound1;
    private javax.swing.JTable tabelaCliente;
    private rojeru_san.rsfield.RSTextFullRound txt1;
    private rojeru_san.rsfield.RSPassViewRound txt2;
    private rojeru_san.rsfield.RSTextFullRound txt3;
    private rojeru_san.rsfield.RSTextFullRound txt4;
    private rojeru_san.rsfield.RSPassViewRound txt5;
    private rojeru_san.rsfield.RSTextFullRound txtIdUserold;
    // End of variables declaration//GEN-END:variables

}
