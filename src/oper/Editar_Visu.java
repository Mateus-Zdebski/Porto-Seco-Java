/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package oper;

import conecta.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import mapeamento_poo.Cargas;

/**
 *
 * @author Mateus
 */
public class Editar_Visu extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int qtRec = 0;
    ArrayList<Cargas> lista = new ArrayList<>();

    /**
     * Creates new form Editar_Visu
     */
    public Editar_Visu() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
        bui.setNorthPane(null);
        conexao = Conexao.conector();

        inicializarTabela();
    }

    private void inicializarTabela() {
        tabelaCargas.setRowHeight(30);
        listarcargas();
        qtRecord();
    }

    public void alterar() {
        String sql = "update cargas set tipo = ?, quantidade = ?, peso = ?, dimensao = ?, origem = ?, destino = ?, transportadora = ?, documentacao = ?, data_chegada = ?, data_saida = ?, status = ? where id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtTipo.getText());
            pst.setInt(2, Integer.parseInt(txtQuantidade.getText()));
            pst.setFloat(3, Float.parseFloat(txtPeso.getText()));
            pst.setString(4, txtDimensao.getText());
            pst.setString(5, txtOrigem.getText());
            pst.setString(6, txtDestino.getText());
            pst.setString(7, txtTransportadora.getText());
            pst.setString(8, txtDocumentacao.getText());
            pst.setString(9, txtDataChegada.getText());
            pst.setString(10, txtDataSaida.getText());

            pst.setInt(12, Integer.parseInt(txtIdUserold.getText()));

            if (txtIdUserold.getText().isEmpty() || txtTipo.getText().isEmpty() || txtQuantidade.getText().isEmpty() || txtPeso.getText().isEmpty() || txtDimensao.getText().isEmpty() || txtOrigem.getText().isEmpty() || txtDestino.getText().isEmpty() || txtTransportadora.getText().isEmpty() || txtDocumentacao.getText().isEmpty() || txtDataChegada.getText().isEmpty() || txtDataSaida.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int alterado = pst.executeUpdate();
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ArrayList<Cargas> consultaCarga() {
        String sql = "SELECT * FROM cargas";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Cargas carga = new Cargas();
                carga.setId(rs.getInt("id"));
                carga.setTipo(rs.getString("tipo"));
                carga.setQuantidade(rs.getInt("quantidade"));
                carga.setPeso(rs.getFloat("peso"));
                carga.setDimensao(rs.getString("dimensao"));
                carga.setOrigem(rs.getString("origem"));
                carga.setDestino(rs.getString("destino"));
                carga.setTransportadora(rs.getString("transportadora"));
                carga.setDocumentacao(rs.getString("documentacao"));
                carga.setDataChegada(rs.getString("data_chegada"));
                carga.setDataSaida(rs.getString("data_saida"));
                carga.setStatus(rs.getString("status"));

                lista.add(carga);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar carga: " + e.getMessage());
        }

        return lista;
    }

    public void qtRecord() {
        String sql = "SELECT COUNT(*) quantidade FROM cargas";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                qtRec = rs.getInt(1);
            }
            lblqtRegistro.setText(String.valueOf(qtRec));

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Consultar Cargas" + erro);
        }
    }

    private void selecaotabela() {
        int row = tabelaCargas.getSelectedRow();
        String table_click = (tabelaCargas.getModel().getValueAt(row, 0).toString());

        String sql = "SELECT * FROM cargas WHERE id='" + table_click + "'";
        try {
            pst = conexao.prepareStatement(sql);
            ResultSet rs;
            rs = pst.executeQuery();
            while (rs.next()) {
                String add1 = rs.getString("id");
                txtIdUserold.setText(add1);
                String add2 = rs.getString("tipo");
                txtTipo.setText(add2);
                int add3 = rs.getInt("quantidade");
                txtQuantidade.setText(String.valueOf(add3));
                float add4 = rs.getFloat("peso");
                txtPeso.setText(String.valueOf(add4));
                String add5 = rs.getString("dimensao");
                txtDimensao.setText(add5);
                String add6 = rs.getString("origem");
                txtOrigem.setText(add6);
                String add7 = rs.getString("destino");
                txtDestino.setText(add7);
                String add8 = rs.getString("transportadora");
                txtTransportadora.setText(add8);
                String add9 = rs.getString("documentacao");
                txtDocumentacao.setText(add9);
                String add10 = rs.getString("data_chegada");
                txtDataChegada.setText(add10);
                String add11 = rs.getString("data_saida");
                if (add11 != null) {
                    txtDataSaida.setText(add11);
                }

            }
        } catch (Exception e) {
            // Trate a exceção aqui
        }
    }

    private void listarcargas() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabelaCargas.getModel();
            model.setNumRows(0);
            ArrayList<Cargas> lista = consultaCarga();

            for (int num = 0; num < lista.size(); num++) {
                model.addRow(new Object[]{
                    lista.get(num).getId(),
                    lista.get(num).getTipo(),
                    lista.get(num).getQuantidade(),
                    lista.get(num).getPeso(),
                    lista.get(num).getDimensao(),
                    lista.get(num).getOrigem(),
                    lista.get(num).getDestino(),
                    lista.get(num).getTransportadora(),
                    lista.get(num).getDocumentacao(),
                    lista.get(num).getDataChegada(),
                    lista.get(num).getDataSaida(),
                    lista.get(num).getStatus()
                });
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Listar Cargas" + erro);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        consultaCargas = new rojeru_san.rsfield.RSTextFullRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCargas = new javax.swing.JTable();
        rSButtonRound1 = new rojerusan.RSButtonRound();
        jLabel2 = new javax.swing.JLabel();
        lblqtRegistro = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtIdUserold = new rojeru_san.rsfield.RSTextFullRound();
        jLabel6 = new javax.swing.JLabel();
        txtTipo = new rojeru_san.rsfield.RSTextFullRound();
        jLabel7 = new javax.swing.JLabel();
        txtQuantidade = new rojeru_san.rsfield.RSTextFullRound();
        jLabel8 = new javax.swing.JLabel();
        txtPeso = new rojeru_san.rsfield.RSTextFullRound();
        jLabel9 = new javax.swing.JLabel();
        txtDimensao = new rojeru_san.rsfield.RSTextFullRound();
        jLabel10 = new javax.swing.JLabel();
        txtOrigem = new rojeru_san.rsfield.RSTextFullRound();
        jLabel11 = new javax.swing.JLabel();
        txtDestino = new rojeru_san.rsfield.RSTextFullRound();
        jLabel12 = new javax.swing.JLabel();
        txtTransportadora = new rojeru_san.rsfield.RSTextFullRound();
        jLabel13 = new javax.swing.JLabel();
        txtDocumentacao = new rojeru_san.rsfield.RSTextFullRound();
        jLabel14 = new javax.swing.JLabel();
        txtDataChegada = new rojeru_san.rsfield.RSTextFullRound();
        jLabel16 = new javax.swing.JLabel();
        txtDataSaida = new rojeru_san.rsfield.RSTextFullRound();
        rSButtonRound3 = new rojerusan.RSButtonRound();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(104, 34, 139));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel1.setText("Digite o nome da carga:");

        consultaCargas.setBackground(new java.awt.Color(104, 34, 139));
        consultaCargas.setText("Digite o nome da carga");

        tabelaCargas.setBackground(new java.awt.Color(255, 255, 255));
        tabelaCargas.setForeground(new java.awt.Color(255, 255, 255));
        tabelaCargas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tipo", "Quantidade", "Peso", "Dimensão", "Origem", "Destino", "Transportadora", "Documentação", "data_da_chegada", "data_da_saida"
            }
        ));
        tabelaCargas.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tabelaCargas.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(tabelaCargas);

        rSButtonRound1.setText("Editar");
        rSButtonRound1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel2.setText("Numeros de cargas:");

        lblqtRegistro.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(515, Short.MAX_VALUE)
                .addComponent(rSButtonRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(455, 455, 455))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(465, 465, 465)
                                    .addComponent(jLabel1))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(296, 296, 296)
                                    .addComponent(consultaCargas, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(27, 27, 27)
                            .addComponent(lblqtRegistro)
                            .addGap(121, 121, 121)))
                    .addGap(3, 3, 3)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(654, Short.MAX_VALUE)
                .addComponent(rSButtonRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addComponent(consultaCargas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(32, 32, 32)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(lblqtRegistro))
                    .addContainerGap(118, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel2.setBackground(new java.awt.Color(104, 34, 139));

        jLabel5.setText("ID:");

        txtIdUserold.setBackground(new java.awt.Color(104, 34, 139));
        txtIdUserold.setText("rSTextFullRound2");

        jLabel6.setText("Tipo:");

        txtTipo.setBackground(new java.awt.Color(104, 34, 139));
        txtTipo.setText("rSTextFullRound3");

        jLabel7.setText("Quantidade:");

        txtQuantidade.setBackground(new java.awt.Color(104, 34, 139));
        txtQuantidade.setText("rSTextFullRound4");

        jLabel8.setText("Peso:");

        txtPeso.setBackground(new java.awt.Color(104, 34, 139));
        txtPeso.setText("rSTextFullRound5");

        jLabel9.setText("Dimensão:");

        txtDimensao.setBackground(new java.awt.Color(104, 34, 139));
        txtDimensao.setText("rSTextFullRound6");

        jLabel10.setText("Origem:");

        txtOrigem.setBackground(new java.awt.Color(104, 34, 139));
        txtOrigem.setText("rSTextFullRound7");

        jLabel11.setText("Destino:");

        txtDestino.setBackground(new java.awt.Color(104, 34, 139));
        txtDestino.setText("rSTextFullRound8");

        jLabel12.setText("Transportadora:");

        txtTransportadora.setBackground(new java.awt.Color(104, 34, 139));
        txtTransportadora.setText("rSTextFullRound9");

        jLabel13.setText("Documentação:");

        txtDocumentacao.setBackground(new java.awt.Color(104, 34, 139));
        txtDocumentacao.setText("rSTextFullRound10");

        jLabel14.setText("Data da Chegada:");

        txtDataChegada.setBackground(new java.awt.Color(104, 34, 139));
        txtDataChegada.setText("rSTextFullRound11");

        jLabel16.setText("Data da Saida:");

        txtDataSaida.setBackground(new java.awt.Color(104, 34, 139));
        txtDataSaida.setText("rSTextFullRound12");

        rSButtonRound3.setText("Editar");
        rSButtonRound3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1170, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(131, 131, 131)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(29, 29, 29)
                                    .addComponent(txtIdUserold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(29, 29, 29)
                                        .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDimensao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPeso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtOrigem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13))
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(34, 34, 34)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTransportadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDocumentacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDataChegada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDataSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(400, 400, 400)
                            .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(132, 132, 132)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 775, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel11)
                        .addComponent(txtIdUserold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(39, 39, 39)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(txtTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(41, 41, 41)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtDocumentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(33, 33, 33)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel14)
                        .addComponent(txtDataChegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(29, 29, 29)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDimensao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel16)
                        .addComponent(txtDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(23, 23, 23)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                    .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(104, 104, 104)))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rSButtonRound1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound1ActionPerformed
        selecaotabela();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_rSButtonRound1ActionPerformed

    private void rSButtonRound3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound3ActionPerformed
        alterar();
    }//GEN-LAST:event_rSButtonRound3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.rsfield.RSTextFullRound consultaCargas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblqtRegistro;
    private rojerusan.RSButtonRound rSButtonRound1;
    private rojerusan.RSButtonRound rSButtonRound3;
    private javax.swing.JTable tabelaCargas;
    private rojeru_san.rsfield.RSTextFullRound txtDataChegada;
    private rojeru_san.rsfield.RSTextFullRound txtDataSaida;
    private rojeru_san.rsfield.RSTextFullRound txtDestino;
    private rojeru_san.rsfield.RSTextFullRound txtDimensao;
    private rojeru_san.rsfield.RSTextFullRound txtDocumentacao;
    private rojeru_san.rsfield.RSTextFullRound txtIdUserold;
    private rojeru_san.rsfield.RSTextFullRound txtOrigem;
    private rojeru_san.rsfield.RSTextFullRound txtPeso;
    private rojeru_san.rsfield.RSTextFullRound txtQuantidade;
    private rojeru_san.rsfield.RSTextFullRound txtTipo;
    private rojeru_san.rsfield.RSTextFullRound txtTransportadora;
    // End of variables declaration//GEN-END:variables

   
}
