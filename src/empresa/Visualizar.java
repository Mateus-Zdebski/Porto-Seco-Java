/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package empresa;

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
public class Visualizar extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int qtRec = 0;
    ArrayList<Cargas> lista = new ArrayList<>();

    /**
     * Creates new form Visualizar
     */
    public Visualizar() {
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

    private void listarcargas() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabelaCargas.getModel();
            model.setRowCount(0);

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        consultaCargas = new rojeru_san.rsfield.RSTextFullRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCargas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblqtRegistro = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel1.setText("Digite o nome da carga:");

        consultaCargas.setBackground(new java.awt.Color(102, 0, 102));
        consultaCargas.setText("Digite o nome da carga");

        tabelaCargas.setBackground(new java.awt.Color(255, 255, 255));
        tabelaCargas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "tipo", "quantidade", "Peso", "dimensão", "origem ", "destino", "tranportadorsa", "documentação", "data da chegada", "data da saida", "status"
            }
        ));
        tabelaCargas.setSelectionBackground(new java.awt.Color(0, 0, 0));
        tabelaCargas.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane1.setViewportView(tabelaCargas);

        jLabel2.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel2.setText("Numeros de cargas:");

        lblqtRegistro.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(465, 465, 465)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(296, 296, 296)
                                .addComponent(consultaCargas, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 326, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(lblqtRegistro)
                .addGap(127, 127, 127))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(consultaCargas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblqtRegistro))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.rsfield.RSTextFullRound consultaCargas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblqtRegistro;
    private javax.swing.JTable tabelaCargas;
    // End of variables declaration//GEN-END:variables

}
