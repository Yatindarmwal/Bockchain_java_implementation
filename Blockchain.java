/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author Yatin
 */
 class block
{
    int index,nonce;
    float currency;
    String timestamp,previousHash,hash;
    block(int index, String timestamp, String previousHash, float currency)
    {
        this.index=index;
        this.currency=currency;
        this.timestamp=timestamp;
        this.previousHash=previousHash;
        this.hash=calculateHash();
        this.nonce=0;
    }
    public static String getHash(byte[] inputBytes, String algorithm)
    {
        String hashValue="";
        try{
              MessageDigest messageDigest; 
              messageDigest = MessageDigest.getInstance(algorithm);
              messageDigest.update(inputBytes);
              byte[] digestedBytes= messageDigest.digest();
              hashValue =DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
           }
        catch(NoSuchAlgorithmException e)
        {            
        }
        return hashValue;
    }
    
    String calculateHash()
    {
       String c_str=previousHash+timestamp+currency+Integer.toString(index)+Integer.toString(nonce);
       return getHash(c_str.getBytes(),"SHA-256");
    }
     public void mineblock(int dificulty)
    {
        StringBuilder sb = new StringBuilder(dificulty);
        for (int i = 0; i < dificulty; ++i)
          {
            sb.append("0");
          }
        String result = sb.toString();
       //  System.out.println(result);
      //   System.out.println(hash.substring(0,dificulty));
        while(!(hash.substring(0,dificulty)).equals(result))
        {
           nonce++; 
           hash=calculateHash();
         //  System.out.println(hash);
        }
        
    }
}
public class Blockchain {
     
     List<block> chain;
     int difficulty=1;
    /**
     * @param args the command line arguments
     */
    public block getLatestBlock()
    {
        return chain.get(chain.size()-1);
    }
    public Blockchain()
    {
       chain = new ArrayList<block>();
       chain.add(new block(0,"1/03/2016","Genisis block",0));
    }
    public void addBlock(block newblock)
    {
        newblock.previousHash=getLatestBlock().hash;
        newblock.mineblock(difficulty);
        chain.add(newblock);
        System.out.println("Mined");
         System.out.println("Hash= "+newblock.hash);
         System.out.println("Timestamp= "+newblock.timestamp);
         System.out.println("Index= "+Integer.toString(newblock.index));
         System.out.println("PreviousHash= "+newblock.previousHash);
         System.out.println("nonce= "+newblock.nonce);
         System.out.println("Currency="+newblock.currency);
    }    
 
    public boolean isChainValid()
    {
       for(int i=chain.size()-1;i>0;i--)
       {
          if(!chain.get(i).hash.equals(chain.get(i).calculateHash()))
          {
             return false; 
          }
          if(!chain.get(i).previousHash.equals(chain.get(i-1).hash))
          {
              return false;
          }         
       }
       return true;
    }
   
    public static void main(String[] args)
    {
        System.out.println(" ");
        Blockchain a=new Blockchain();
        a.addBlock(new block(1, "1/03/2016", "Genisis block", 0));
        a.addBlock(new block(2, "5/03/2016", "", 45));
        a.addBlock(new block(3, "7/04/2016", "", 56)); 
        a.chain.get(1).currency=450.7f;
        System.out.println("Chain Valid :: "+a.isChainValid());
    }
}
