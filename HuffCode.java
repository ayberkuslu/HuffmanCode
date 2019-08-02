import java.io.*;

public class HuffCode {
	
			/** toplam kac byte oldugu */
	int count;
	
			/**
			 	*  freq[x] karakterin frekansi 
			 		*  255 ascii uzunlugu  
			 */
	int[] freq = new int[255+1]; 

	
	PQ heap;  
	
	int karakterSayisi;	
	
	String[] ascii = new String[255+1]; 
	
	
	public static void main(String[] args)  {
		
		if(args.length != 2) {
			System.out.println("Args sayisi 2 den eksik yada fazla olamaz. Orn : "
					+ "\njava HuffCode encode input.txt  "
					+ "\njava HuffCode decode input.txt.huff\n" );	
			System.exit(1);
		}
		
		String komut = args[0];
		String fileName = args[1];
		
		HuffCode a = new HuffCode();

		if(komut.equalsIgnoreCase("encode")) {
			if(a.enCode(fileName)) {
			System.out.println("Coding basariyla tamamlandi.");
			
			}else {
				System.out.println("Dosya sikistirilirken hata !");
			}
			}
		if(komut.equalsIgnoreCase("decode")) {
			if(a.deCode(fileName)) {
			System.out.println("Decoding basariyla tamamlandi.");
	      //  System.out.println(fileName);  
	        
//			new File(fileName).delete();

			}else
				System.out.println("Dosya acilirken hata hata !");
		}

	}

	
	
	// baslangic metotu
	private boolean enCode(String fileName)  {
		try {
        getFreq(fileName);
        generateTree(); 
        kodUret(heap.getMin(), "");        
        System.out.println(fileName + ".huff dosyasi yaziliyor.");
        writeFile(fileName);
        System.out.println("Dosya sikistirildi.");
      return  (deleteFile(fileName));
		}catch(Exception e) {
			System.out.println("Dosya sikistirilirken hata!");
			return false;
		}
}
	
	private boolean deCode(String fileName) {
		
		try {
			String temp = fileName;
			BufferedInputStream inputStream = new BufferedInputStream (new FileInputStream(fileName));
			String newFileName = fileName.replace(".huff","");
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFileName));
            System.out.println("olusturulacak dosya adi : "+newFileName 
            		+"\n" + fileName + " dosyasi okunuyor.");
			karakterSayisi = inputStream.read(); // dosyanin ilk kismi kac farkli karakter olduguydu.
			count = intOku(inputStream);
	    //       new File(temp).delete();

            karakterKoduOku(inputStream);
            
            generateTree();
            huffDosyasiYaz(inputStream,outputStream);
           System.out.println("Islem tamamlandý.");
           inputStream.close();
           outputStream.close();
         //  new File(temp).delete();
           
           return (new File(temp).delete());
           
           
           // return true;
          			
			
		}catch(Exception e) {

			System.out.println("Dosya decode edilirken bulunamadi.");
			return false;
		}
		 
	}
	
	 private void huffDosyasiYaz(BufferedInputStream inputStream,
             BufferedOutputStream outputStream) throws IOException {
     TreeNode node = heap.getMin();
     int key;
     int character;
     System.out.println("Dosya yaziliyor");
     while(true) {
             character = inputStream.read();
             for(int i = 0; i < 8; ++i) {
                     if(node.isLeaf()) {
                             key = node.getKey();
                             outputStream.write(key);
                             node = heap.getMin();
                             count = count-1;
                             if(count == 0) {
                                     break;
                             }
                     }
                     int bit = (character & 128);                   
                     if(bit == 128) {
                             node = node.getRight();
                     }
                     else
                             node = node.getLeft();
                     character <<= 1;
             }
             if(count == 0) {
                     break;
             }
     }
     outputStream.close();
}
	
	
	
	
	private void getFreq(String fileName)  {
		
		System.out.println("Karakter frekanslari hesaplaniyor");
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
            int byt = 0;
            count = 0;
	            while ((byt = bis.read()) != -1) {
	                    freq[byt] = freq[byt] + 1;
	                    count = count+1;
	            }
            bis.close();		
		}catch(IOException e) {
			
			System.out.println("Freq hesaplanirken dosya bulunamadi.");
			System.exit(0);
		}
		
	}
	
	private void makeHeap(int [] freq) {
		System.out.println("Heap olusturuluyor..");
		
		heap = new PQ(257); // ilk eleman dummy
		
        for(int i = 0; i < freq.length; ++i) {
                if(freq[i] != 0) {
                        TreeNode node = new TreeNode(freq[i], i);
                        node.setToLeaf();
                        heap.insert(node);
                }
        }

		
        		
	}
	
	private void generateTree() {
        makeHeap(freq);
        karakterSayisi = heap.getSize();
        
        System.out.println( karakterSayisi+ " farkli karakterden olusan agac olusturuldu.");
        heap = createTree(heap);
		
	}
	
	
	private PQ createTree(PQ heap) {
        int n = heap.getSize();
        for(int i = 0; i < (n-1); ++i) {
                TreeNode z = new TreeNode();
                z.setLeft(heap.delMin());
                z.setRight(heap.delMin());
                z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq());
                heap.insert(z);
        }
        return heap;
}
	
	// kodlari olustur
	private void kodUret(TreeNode node, String kod) {             
        if(node != null) {                      
                if(node.isLeaf()) {
                        ascii[node.getKey()] = kod;
//                        System.out.println((char)node.getKey() + " charýnýn kodu : "+ kod
//                        		+ " gecme sayisi : " + node.getFreq());
                } 
                else {
                        kodUret(node.getLeft(), kod + "0");
                        kodUret(node.getRight(), kod + "1");
                }
        }
}
	
	
	private void writeFile(String fileName) {
        try {
        	
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
             //   String temp = fileName;
                fileName = fileName + ".huff";
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
    			yaz1(outputStream);
    			yaz2(outputStream,inputStream);
                outputStream.close();
               inputStream.close();
//              File a = new File(temp);
//             if(a.delete()) System.out.println(temp + " dosyasi basariyla silindi.");
//             else System.out.println(temp + " dosyasi silinemedi!");

              
        }
        catch(Exception e) {
            System.out.println("writeFile Hata");

                System.out.println(e);
        }
}
	
	private boolean deleteFile(String fileName) {
        String temp = fileName;
        File a = new File(temp);
       if(a.delete()) {
    	   System.out.println(temp + " dosyasi basariyla silindi.");
    	   return true;
       }
       else System.out.println(temp + " dosyasi silinemedi!");
       return false;
		
	}

	 private void yaz1(BufferedOutputStream output) // decode icin gerekli veriler.
		        throws IOException {
		                output.write(karakterSayisi); //kac farkli karakter oldugu
		                intYaz(output, count);  // toplam kac karakter
		                freqYaz(output);   // her karakter kac tane
		                
		                
		        }
	 
	 private void freqYaz(BufferedOutputStream output) throws IOException {
         for (int i = 0; i < ascii.length; ++i) {
                 if(ascii[i] != null) {
                         output.write(i);
                         intYaz(output, freq[i]);
                 }
         }
 }
	 
	 
	 private void yaz2(BufferedOutputStream outputStream, BufferedInputStream inputStream) 
		        throws IOException {
		                String artikKisim = textiYaz(outputStream, inputStream); // texti yazar , 8 e bolunmeyen kisimi bulur
		                eksikByteYaz(outputStream, artikKisim); // fazlaligi yazar
		        }
	 
	 
	private static void intYaz(BufferedOutputStream outputStream, int deger) throws IOException {
	        String hexString = Integer.toHexString(deger);
	        String hex = "";
	        for(int i = 0; i < 8 - hexString.length(); ++i) {
	                hex = hex + "0";                
	        }
	        hex = hex + hexString;
	        for(int i = 0; i < 4; ++i) {
	                String yazilacakKisim = hex.substring(0, 2);
	                outputStream.write(Integer.parseInt(yazilacakKisim, 16));
	                hex = hex.substring(2);
	        }
	}
	
	
	 private void eksikByteYaz(BufferedOutputStream outputStream, String artikKisim) throws IOException {
         boolean[] bits = new boolean[8];
         int yazilacakByte;
         if(artikKisim.length() > 0) {
                 int fark = 8 - artikKisim.length();
                 //System.out.println("Eksik byte sayisi " + fark);
                 for(int i = 0; i < artikKisim.length(); ++i) {
                         if(i > fark) {
                                 bits[i] = false;
                         }
                         else {
                                 if(artikKisim.charAt(i) == '1')
                                         bits[i] = true;
                                 else
                                         bits[i] = false;
                         }
                 }
         }
         yazilacakByte = bitsToByte(bits);
         outputStream.write(yazilacakByte);
 }
	 
	 
	 
	 
	 
	 private static int bitsToByte(boolean[] bits) {
         if (bits == null || bits.length != 8) {
                 throw new IllegalArgumentException();
         }
         int data = 0;
         for (int j = 0; j < 8; j++) {
                 if (bits[j]) data += (1 << (7-j));
         }
         return data;
 }
	 
	 
	 private String textiYaz(BufferedOutputStream outputStream,BufferedInputStream inputStream)throws IOException {
     String artikKisim = "";
     boolean[] bits = new boolean[8];
     
     for(int i = 0 ; i< bits.length ; i++)
    	 bits[i] = false;

     
     int okunacakByte;
     int yazilacakByte = 0;

     while ((okunacakByte = inputStream.read()) != -1) {
             String yazilacakChar = ascii[okunacakByte];             
             artikKisim = artikKisim + yazilacakChar;
             while(artikKisim.length() >= 8) {   
                     for(int j = 0; j < 8; ++j) {
                             if(artikKisim.charAt(j) == '1') 
                                     bits[j] = true;
                             else if(artikKisim.charAt(j) == '0'  )
                                     bits[j] = false;
                     }
                     artikKisim = artikKisim.substring(8);
                     yazilacakByte = bitsToByte(bits);
                     outputStream.write(yazilacakByte);
             }                       
     }
     return artikKisim;
}
	
	
	 private static int intOku(BufferedInputStream inputStream) throws IOException {
         int integer = 0;
         for(int i = 0; i < 4; ++i) {
                 int byteR = inputStream.read();
                 for(int j = 0; j < 8; ++j) {
                         int bit = (byteR & 128); //  128 = binary 10000000 
                         if(bit == 128) {
                                 integer *= 2;
                                 integer += 1;
                         }
                         else {
                                 integer *= 2;
                         }
                         byteR *= 2;
                 }
         }
         return integer;
 }
	
	 
	 private void karakterKoduOku(BufferedInputStream inputStream)
		        throws IOException {
		                ascii = new String[256];
		                for(int i = 0; i < karakterSayisi; ++i) {                              
		                        int karakter = inputStream.read();
		                        freq[karakter] = intOku(inputStream);
		                }
		        }
	 
}







