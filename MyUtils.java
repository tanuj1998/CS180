
public class MyUtils 
{
	public static boolean isNumeric(String str)
	{
		char c;
		char next;
		int i ;
		if(str.length()==0)
		{
			return false;
		}
		else
		{
			int len = str.length();
			for(i=0; i<len ; i++)
			{
				c = str.charAt(i);
				if(c>=48 && c<=57)
				{
					continue;
				}
				else if(c==46)
				{
					next = str.charAt(i+1);
					if(next>=48 && next<=57)
					{
						i++;
						continue;
					}
					else
					{
						return false;
					}					
				}
				else
				{
					return false;
				}				
			}
		}
		return true;
	}
	
	public static String formatStr(String str)
	{
		String lower = str.toLowerCase();
	    char first = lower.charAt(0);
	    String s = Character.toString(first);
	    String upper = s.toUpperCase();
	    lower = lower.substring(1, lower.length());
	    String fin = upper + lower;
	    return fin;		
	}
}
