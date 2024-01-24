/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package admin;

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
public class Editar_cargas extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int qtRec = 0;
    ArrayList<Cargas> lista = new ArrayList<>();

    /**
     * Creates new form Editar_cargas
     */
    public Editar_cargas() {
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

    private void adicionar() {
        String sql = "INSERT INTO cargas (tipo, quantidade, peso, dimensao, origem, destino, transportadora, documentacao, data_chegada, data_saida, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Cargas carga = new Cargas();

        carga.setTipo(txtTipo.getText());
        carga.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
        carga.setPeso(Float.parseFloat(txtPeso.getText()));
        carga.setDimensao(txtDimensao.getText());
        carga.setOrigem(txtOrigem.getText());
        carga.setDestino(txtDestino.getText());
        carga.setTransportadora(txtTransportadora.getText());
        carga.setDocumentacao(txtDocumentacao.getText());
        carga.setDataChegada(txtDataChegada.getText());
        carga.setDataSaida(txtDataSaida.getText());
        carga.setStatus(comboBoxStatus.getSelectedItem().toString());

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, carga.getTipo());
            pst.setInt(2, carga.getQuantidade());
            pst.setFloat(3, carga.getPeso());
            pst.setString(4, carga.getDimensao());
            pst.setString(5, carga.getOrigem());
            pst.setString(6, carga.getDestino());
            pst.setString(7, carga.getTransportadora());
            pst.setString(8, carga.getDocumentacao());
            pst.setString(9, carga.getDataChegada());
            pst.setString(10, carga.getDataSaida());
            pst.setString(11, carga.getStatus());

            if (txtTipo.getText().isEmpty() || txtQuantidade.getText().isEmpty() || txtPeso.getText().isEmpty() || txtOrigem.getText().isEmpty() || txtDestino.getText().isEmpty() || txtTransportadora.getText().isEmpty() || txtDocumentacao.getText().isEmpty() || txtDataChegada.getText().isEmpty() || comboBoxStatus.getSelectedItem().toString().isEmpty()) {
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
    }//Fim do método adicionar

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
            pst.setString(11, (String) comboBoxStatus.getSelectedItem());
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

    private void excluir() {
        String sql = "DELETE FROM cargas WHERE id = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUserold.getText());

            int excluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (excluir == JOptionPane.YES_OPTION) {
                int excluido = pst.executeUpdate();
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso");
                    txtTipo.setText(null);
                    txtQuantidade.setText(null);
                    txtPeso.setText(null);
                    txtDimensao.setText(null);
                    txtOrigem.setText(null);
                    txtDestino.setText(null);
                    txtTransportadora.setText(null);
                    txtDocumentacao.setText(null);
                    txtDataChegada.setText(null);
                    txtDataSaida.setText(null);
                    comboBoxStatus.setSelectedItem(null);

                    // Adicione aqui a limpeza dos outros campos, se necessário
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
                String add12 = rs.getString("status");
                comboBoxStatus.setSelectedItem(add12);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        rSButtonRound2 = new rojerusan.RSButtonRound();
        jLabel2 = new javax.swing.JLabel();
        lblqtRegistro = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtIdUserold = new rojeru_san.rsfield.RSTextFullRound();
        txtTipo = new rojeru_san.rsfield.RSTextFullRound();
        txtQuantidade = new rojeru_san.rsfield.RSTextFullRound();
        txtPeso = new rojeru_san.rsfield.RSTextFullRound();
        txtDimensao = new rojeru_san.rsfield.RSTextFullRound();
        txtOrigem = new rojeru_san.rsfield.RSTextFullRound();
        txtDestino = new rojeru_san.rsfield.RSTextFullRound();
        txtTransportadora = new rojeru_san.rsfield.RSTextFullRound();
        txtDocumentacao = new rojeru_san.rsfield.RSTextFullRound();
        txtDataChegada = new rojeru_san.rsfield.RSTextFullRound();
        txtDataSaida = new rojeru_san.rsfield.RSTextFullRound();
        comboBoxStatus = new javax.swing.JComboBox<>();
        rSButtonRound3 = new rojerusan.RSButtonRound();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(104, 34, 139));

        jPanel1.setBackground(new java.awt.Color(104, 34, 139));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 0, 16)); // NOI18N
        jLabel1.setText("Digite o nome da carga:");

        consultaCargas.setBackground(new java.awt.Color(104, 34, 139));
        consultaCargas.setText("Digite o nome da carga");

        tabelaCargas.setBackground(new java.awt.Color(104, 34, 139));
        tabelaCargas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tipo", "Quantidade", "Peso", "Dimensão", "Origem", "Destino", "Transportadora", "Documentação", "Data da chegada", "Data da saida", "Status"
            }
        ));
        jScrollPane1.setViewportView(tabelaCargas);

        rSButtonRound1.setText("Editar");
        rSButtonRound1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound1ActionPerformed(evt);
            }
        });

        rSButtonRound2.setText("Excluir");
        rSButtonRound2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRound2ActionPerformed(evt);
            }
        });

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(rSButtonRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(rSButtonRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSButtonRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel2.setBackground(new java.awt.Color(104, 34, 139));

        jLabel4.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        jLabel4.setText("Cadastramento de carga");

        jLabel5.setText("ID:");

        jLabel6.setText("Tipo:");

        jLabel7.setText("Quantidade:");

        jLabel8.setText("Peso:");

        jLabel9.setText("Dimensão:");

        jLabel10.setText("Origem:");

        jLabel11.setText("Destino:");

        jLabel12.setText("Transportadora:");

        jLabel13.setText("Documentação:");

        jLabel14.setText("Data da Chegada:");

        jLabel15.setText("Status:");

        jLabel16.setText("Data da Saida:");

        txtIdUserold.setEditable(false);
        txtIdUserold.setBackground(new java.awt.Color(104, 34, 139));

        txtTipo.setBackground(new java.awt.Color(104, 34, 139));
        txtTipo.setText("rSTextFullRound3");

        txtQuantidade.setBackground(new java.awt.Color(104, 34, 139));
        txtQuantidade.setText("rSTextFullRound4");

        txtPeso.setBackground(new java.awt.Color(104, 34, 139));
        txtPeso.setText("rSTextFullRound5");

        txtDimensao.setBackground(new java.awt.Color(104, 34, 139));
        txtDimensao.setText("rSTextFullRound6");

        txtOrigem.setBackground(new java.awt.Color(104, 34, 139));
        txtOrigem.setText("rSTextFullRound7");

        txtDestino.setBackground(new java.awt.Color(104, 34, 139));
        txtDestino.setText("rSTextFullRound8");

        txtTransportadora.setBackground(new java.awt.Color(104, 34, 139));
        txtTransportadora.setText("rSTextFullRound9");

        txtDocumentacao.setBackground(new java.awt.Color(104, 34, 139));
        txtDocumentacao.setText("rSTextFullRound10");

        txtDataChegada.setBackground(new java.awt.Color(104, 34, 139));
        txtDataChegada.setText("rSTextFullRound11");

        txtDataSaida.setBackground(new java.awt.Color(104, 34, 139));
        txtDataSaida.setText("rSTextFullRound12");

        comboBoxStatus.setBackground(new java.awt.Color(104, 34, 139));
        comboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(jLabel4)
                        .addGap(0, 55, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
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
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTransportadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDocumentacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDataChegada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDataSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboBoxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(223, 223, 223))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel4)
                .addGap(48, 48, 48)
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
                    .addComponent(txtOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(comboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(rSButtonRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 6, 1170, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rSButtonRound1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound1ActionPerformed
        selecaotabela();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_rSButtonRound1ActionPerformed

    private void rSButtonRound2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound2ActionPerformed
        excluir();
    }//GEN-LAST:event_rSButtonRound2ActionPerformed

    private void rSButtonRound3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRound3ActionPerformed
        alterar();
    }//GEN-LAST:event_rSButtonRound3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxStatus;
    private rojeru_san.rsfield.RSTextFullRound consultaCargas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
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
    private rojerusan.RSButtonRound rSButtonRound2;
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
