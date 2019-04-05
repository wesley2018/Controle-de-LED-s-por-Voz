package com.example.voz_led_controle; // NOME DO PACOTE REFERENTE A CLASSE

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

public class VozActivity extends ActionBarActivity implements OnClickListener {
	
	// DECLARA��O DE VARI�VEIS
	ImageButton btVoz,btConectar;
	TextView tvInternet,tvStatusA,tvStatusB,tvStatusC;
	EditText et_Ip;
	String L,N,M, hostIp = null, comando;
	Handler mHandler;
	long lastPress;
	public final int REQ_CODE_SPEECH_INPUT = 100;
	boolean palavra = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO M�TODO "telaIp"
	}
	// M�TODO "telaIp"
		public void telaIp(){
			setContentView(R.layout.tela_ip); // INICIALIZA A TELA
			et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
			
	    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
	        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
	    	
	    	if(btConectar.isPressed()){ // SE O BOT�O FOR PRESSIONADO
	    		onClick(btConectar); // EXECUTA A FUN��O REFERENTE AO BOT�O
	    	}
	    }
		// M�TODO "telaPrincipal"
		public void telaPrincipal(){   	
			setContentView(R.layout.activity_voz); // INICIALIZA A TELA
			
			mHandler = new Handler(); // VARI�VEL "mHandler" INICIALIZADA
	        mHandler.post(mUpdate);	 // VARI�VEL "mHandler" CHAMA O M�TODO "mUpdate"
	        /**MASTERWALKER SHOP*/
			
	    	tvStatusA = (TextView) findViewById(R.id.tvStatusA); // ESTANCIA O TEXTVIEW
	     	tvStatusB = (TextView) findViewById(R.id.tvStatusB);
	     	tvStatusC = (TextView) findViewById(R.id.tvStatusC);
	     	/**MASTERWALKER SHOP*/
	     	
	     	btVoz = (ImageButton) findViewById(R.id.btVoz); // ESTANCIA O IMAGEBUTTON
	        btVoz.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
	        /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if(btVoz.isPressed()){ // SE O BOT�O FOR PRESSIONADO
				onClick(btVoz); // EXECUTA A FUN��O REFERENTE AO BOT�O
			}
		}/**MASTERWALKER SHOP*/
		// M�TODO QUE EXECUTA A ATUALIZA��O DO TEXTVIEW COM INFORMA��O RECEBIDA DO ARDUINO
		private Runnable mUpdate = new Runnable() {/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	    	public void run() { /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	    		arduinoStatus("http://"+hostIp+"/"); // CHAMA O M�TODO "arduinoStatus"
	    		mHandler.postDelayed(this, 1000); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMA��O (500 MILISEGUNDOS)
	    	}
	    };/**MASTERWALKER SHOP*/
	 // M�TODO "arduinoStatus"
	    public void arduinoStatus(String urlArduino){
	    	/**MASTERWALKER SHOP*/
			String urlHost = urlArduino; // CRIA UMA STRING
			String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
			/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			//INICIO DO TRY CATCH
			try{
				respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
				String resposta = respostaRetornada.toString(); // STRING "resposta"
				resposta = resposta.replaceAll("\\s+", ""); 
				/**MASTERWALKER SHOP*/
				String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"  	     
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				if(b[0].equals("AC1")){ // SE POSI��O 0 DO VETOR IGUAL A "AC1"				
					tvStatusA.setText("ON"); // TEXTVIEW RECEBE ON
				}
				else{
					if(b[0].equals("AP1")){  // SE POSI��O 0 DO VETOR IGUAL A "AP1"	
						tvStatusA.setText("OFF"); // TEXTVIEW RECEBE OFF				
					}
				}			
				if(b[1].equals("AC2")){				
					tvStatusB.setText("ON");				
				}
				else{
					if(b[1].equals("AP2")){
						tvStatusB.setText("OFF");					
					}
				}
				if(b[2].equals("AC3")){
					tvStatusC.setText("ON");				
				}
				else{
					if(b[2].equals("AP3")){
						tvStatusC.setText("OFF");					
					}
				}
			}
			catch(Exception erro){/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			}
		}
	    /**MASTERWALKER SHOP*/
		@Override
		public void onClick(View bt) { // M�TODO QUE GERENCIA OS CLICK'S NOS BOT�ES
			/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if(bt == btConectar){ // SE BOT�O CLICKADO
				if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
					Toast.makeText(getApplicationContext(), // FUN��O TOAST
					"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
				}else{ // SEN�O
				hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
				// FUN��O QUE OCULTA O TECLADO AP�S CLICAR EM CONECTAR
				InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
			    Context.INPUT_METHOD_SERVICE);
			    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
				telaPrincipal(); // FAZ A CHAMADA DO M�TODO "telaPrincipal"
				}	
			}
			/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if(bt == btVoz){ // SE BOT�O CLICKADO
				promptSpeechInput(); // CHAMA O M�TODO "promptSpeechInput"
			}	
		}
		//M�TODO RESPONS�VEL POR PROCESSAR O COMANDO DE VOZ
		private void promptSpeechInput() {/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, 
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
					getString(R.string.speech_prompt));
			try {/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
			} catch (ActivityNotFoundException a) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.speech_not_supported),
						Toast.LENGTH_SHORT).show();
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		// SEGUNDO M�TODO RESPONS�VEL POR PROCESSAR O COMANDO DE VOZ
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);

			switch (requestCode) {/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			case REQ_CODE_SPEECH_INPUT: {
				if (resultCode == RESULT_OK && null != data) {

					ArrayList<String> result = data
							.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
					comando = result.get(0);
					EnviarComandoArduino(comando); // PASSA O PAR�METRO PARA O M�TODO "EnviarComandoArduino"
				}
				break;/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			}
			}
		}
		@SuppressLint("DefaultLocale")
		//M�TODO RESPONS�VEL POR  ENVIAR O COMANDO AO ARDUINO DE ACORDO COM O COMANDO DE VOZ DADO
		public void EnviarComandoArduino(String palavra){
			String url = null; // DEFINE UMA STRING DE VALOR NULO
			if (comando.equals("acender led 1") || (comando.equals("acender led um"))){ // SE STRING "comando" FOI IGUAL A ALGUM DOS COMPARATIVOS EXECUTA O COMANDO
				url = "http://"+hostIp+"/?L=1"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}
			if (comando.equals("apagar 1") || (comando.equals("apagar um"))){ // O COMPARATIVO "ou" � REPRESENTANDO POR "||"
				url = "http://"+hostIp+"/?L=0"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}
			if (comando.equals("acender led 2") || (comando.equals("acender led dois"))){
				url = "http://"+hostIp+"/?M=1"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}
			if (comando.equals("apagar 2") || (comando.equals("apagar dois"))){
				url = "http://"+hostIp+"/?M=0"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}
			if (comando.equals("acender led 3") || (comando.equals("acender led tr�s"))){
				url = "http://"+hostIp+"/?N=1"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if (comando.equals("apagar 3") || (comando.equals("apagar tr�s"))){
				url = "http://"+hostIp+"/?N=0"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
			}
			//EXIBE UM ALERTA COM O QUE FOI DETECTADO PELA APLICA��O
			Toast.makeText(getApplicationContext(), // FUN��O TOAST
					"COMANDO: "+comando.toUpperCase(), Toast.LENGTH_SHORT).show();
		String urlGetHost = url; // CRIA UMA STRING CHAMADA "urlGetHost" QUE RECEBE O VALOR DA STRING "url"
		//INICIO DO TRY CATCH
		try{
			ConectHttpClient.executaHttpGet(urlGetHost); // PASSA O PAR�METRO PARA O O M�TODO "executaHttpGet" NA CLASSE "ConectHttpClient" E ENVIA AO ARDUINO
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		catch(Exception erro){ // FUN��O DE EXIBI��O DO ERRO
		} // FIM DO TRY CATCH
		}
		/**MASTERWALKER SHOP*/
		// M�TODO QUE VERIFICA O BOT�O DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICA��O SE PRESSIONADO 2 VEZES SEGUIDAS
	    public void onBackPressed() {		
		    long currentTime = System.currentTimeMillis();
		    if(currentTime - lastPress > 5000){
		        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
		        lastPress = currentTime;  
		    }else{ /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		        super.onBackPressed();
		        android.os.Process.killProcess(android.os.Process.myPid());
		    }
		}
}
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/