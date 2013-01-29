import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class ChatClient extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private static Socket s; // 
	SendObject sendObject = new SendObject(null, 1);
	ObjectInputStream inp;
	ObjectOutputStream out;
	String nickname;

	
	public ChatClient() {
		initComponents();
	}




 

	private class Receiver implements Runnable{

		public void run() {
			while (!s.isClosed()) {
				String message = null;

				try {

					try {
						sendObject = (SendObject) inp.readObject();
//						System.out.println("Пришло - " + sendObject.getMessage());
//						System.out.println("Тип - " +  sendObject.getType());
						message = sendObject.getMessage();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				jTextChat.append(message);
				jTextChat.append("\n");
			if (sendObject.getType() == 2) { // если пришло сообщение , что 
					jTextIP.setEnabled(true);
			    	jTextNickname.setEnabled(true);
			    	jButtonConnect.setEnabled(true);
			    	jButtonDisconnect.setEnabled(false);
			    	jButtonSend.setEnabled(false);
				return ;
				}
			
			}
			
			
			
		}
	}
	
	
	//JAVA_PANEL
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	
    	jScrollPane2 = new javax.swing.JScrollPane();
    	jScrollPane1 = new javax.swing.JScrollPane();
        jTextChat = new javax.swing.JTextArea();
        jButtonConnect = new javax.swing.JButton();
        jButtonDisconnect = new javax.swing.JButton();
        jButtonSend = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextIP = new javax.swing.JTextField();
        jTextNickname = new javax.swing.JTextField();
        jTextMessage = new javax.swing.JTextArea();

 

        
        
        DefaultCaret caretChatArea = (DefaultCaret)jTextChat.getCaret();
        caretChatArea.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextChat.setColumns(20);
        jTextChat.setRows(5);
        jScrollPane2.setViewportView(jTextChat);

        jTextMessage.setColumns(20);
        jTextMessage.setRows(5);
        jScrollPane1.setViewportView(jTextMessage);

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jLabel1.setText("IP Address");

        jTextIP.setText("localhost");
        jTextIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIPActionPerformed(evt);
            }
        });

        jLabel2.setText("Nickname");

        jTextNickname.setText("Nickname");

        jButtonConnect.setText("Connect");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jButtonDisconnect.setText("Disconnect");
        jButtonDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDisconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextIP, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(jButtonConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonConnect)
                        .addGap(31, 31, 31)
                        .addComponent(jButtonDisconnect)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        // TODO add your handling code here:

	try {
				s = new Socket(jTextIP.getText(), 45006);
				sendObject = new SendObject(null, 1);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jButtonConnect.setEnabled(true);
				jTextChat.setText("Сервер не найден");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				jButtonConnect.setEnabled(true);
				jTextChat.setText("Ошибка соединения");
				e.printStackTrace();
			}
    		nickname = jTextNickname.getText();
    		
    	

    			try {
					out = new ObjectOutputStream(s.getOutputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // выходной
		
    		
    			InputStream ips;
				try {
					ips = s.getInputStream();
					inp = new ObjectInputStream(ips);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //входной
		

    			if (!nickname.isEmpty()) { // ник не пустой

    			sendObject.setMessage(nickname);
    			sendObject.setType(1);

    			try {
					out.writeObject(sendObject);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			}
    			else //ник пустой
    			{
    				jTextChat.setText("Ник пустой!\n");
    				disconnect();
    				return;
    			}
    		
		new Thread(new Receiver()).start();// создаем поток чтения 
    	jTextIP.setEnabled(false);
    	jTextNickname.setEnabled(false);
    	jButtonConnect.setEnabled(false);
    	jButtonDisconnect.setEnabled(true);
    	jButtonSend.setEnabled(true);
		

    }//GEN-LAST:event_jButtonConnectActionPerformed

    private void jButtonDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDisconnectActionPerformed
        // TODO add your handling code here:
	disconnect();
    }//GEN-LAST:event_jButtonDisconnectActionPerformed


    private void disconnect() {
    	try {
    		sendObject.setMessage("user:disconnect");
    		sendObject.setType(1);
    		out.writeObject(sendObject);
    		out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
    	jTextIP.setEnabled(true);
    	jTextNickname.setEnabled(true);
    	jButtonConnect.setEnabled(true);
    	jButtonDisconnect.setEnabled(false);
    	jButtonSend.setEnabled(false);
    }

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        // TODO add your handling code here:
	String message = jTextMessage.getText();
    	if (message.isEmpty() == false) {
    		try {
    		sendObject.setMessage(message);
    		sendObject.setType(1);
    		out.writeObject(sendObject);
    		out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		jTextMessage.setText("");
    	}
    }//GEN-LAST:event_jButtonSendActionPerformed

    private void jTextChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextChatActionPerformed

    private void jTextMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMessageActionPerformed

    private void jTextIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIPActionPerformed

    private void jTextNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNicknameActionPerformed

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
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonDisconnect;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private JTextArea jTextChat;
    private javax.swing.JTextField jTextIP;
    private javax.swing.JTextArea jTextMessage;
    private javax.swing.JTextField jTextNickname;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
