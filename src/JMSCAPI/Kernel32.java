package JMSCAPI;
import java.nio.Buffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * @author Vladimir Bubornev aka 0x002Ah
 *
 */


public interface Kernel32 extends StdCallLibrary{
    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class, 
    		W32APIOptions.UNICODE_OPTIONS);
    
	/**
	 * Retrieves the calling thread's last-error code value. The last-error code
	 * is maintained on a per-thread basis. Multiple threads do not overwrite each
	 * other's last-error code.
	 * 																									<p>
	 * NOTE: Functions executed by the calling thread set this value by calling the
	 * <b>SetLastError</b> function. You should call the GetLastError function immediately
	 * when a function's return value indicates that such a call will return useful
	 * data. That is because some functions call <b>SetLastError</b> with a zero when they
	 * succeed, wiping out the error code set by the most recently failed function.
	 * 																									<p>
	 * @return	The return value is the calling thread's last-error code. 
	 * 																									<p>								
	 * The Return Value section of the documentation for each function that sets
	 * the last-error code notes the conditions under which the function sets the
	 * last-error code. Most functions that set the thread's last-error code set it
	 * when they fail. However, some functions also set the last-error code when they
	 * succeed. If the function is not documented to set the last-error code, the
	 * value returned by this function is simply the most recent last-error code to 
	 * have been set; some functions set the last-error code to 0 on success and
	 * others do not.
	 * 
	 * 
	 */
	int GetLastError();
	
	/* 
	 * Some of possible error codes. (General msdn description.)
	 */

	/**
	 * More data is available.
	 */
	public static final int ERROR_MORE_DATA = 234;
	/**
	 * No more data is available.
	 */
	public static final int ERROR_NO_MORE_ITEMS = 259;
	/**
	 * Not enough storage is available to process this command. 
	 */
	public static final int ERROR_NOT_ENOUGH_MEMORY = 8;
	/**
	 * Invalid flags specified.
	 */
	public static final int NTE_BAD_FLAGS = 0x80090009;
	/**
	 * An internal error occurred.
	 */
	public static final int NTE_FAIL = 0x80090020;
	
	/**
	 * Keyset does not exist
	 */
	public static final int NTE_BAD_KEYSET = 0x80090016;
	/**
	 * The operation was canceled by the user.
	 */
	public static final int ERROR_CANCELLED = 1223;



    /**
     * The FormatMessage function formats a message string. The function requires a
     * message definition as input. The message definition can come from a buffer 
     * passed into the function. It can come from a message table resource in an 
     * already-loaded module. Or the caller can ask the function to search the 
     * system's message table resource(s) for the message definition. The function 
     * finds the message definition in a message table resource based on a message 
     * identifier and a language identifier. The function copies the formatted message 
     * text to an output buffer, processing any embedded insert sequences if requested.
     * @param dwFlags
     *  Formatting options, and how to interpret the lpSource parameter. The low-order
     *  byte of dwFlags specifies how the function handles line breaks in the output 
     *  buffer. The low-order byte can also specify the maximum width of a formatted
     *  output line. <p/>
     * This version of the function assumes FORMAT_MESSAGE_ALLOCATE_BUFFER is
     *  <em>not</em> set.
     * @param lpSource
     *  Location of the message definition.
     * @param dwMessageId
     *  Message identifier for the requested message.
     * @param dwLanguageId
     *  Language identifier for the requested message.
     * @param lpBuffer
     *  Pointer to a buffer that receives the null-terminated string that specifies the 
     *  formatted message.
     * @param nSize
     *  This this parameter specifies the size of the output buffer, in TCHARs. If FORMAT_MESSAGE_ALLOCATE_BUFFER is 
     * @param va_list
     *  Pointer to an array of values that are used as insert values in the formatted message.
     * @return
     * 	If the function succeeds, the return value is the number of TCHARs stored in 
     * 	the output buffer, excluding the terminating null character. If the function 
     * 	fails, the return value is zero. To get extended error information, call 
     *  GetLastError.
     */
    int FormatMessage(int dwFlags, Pointer lpSource, int dwMessageId,
                      int dwLanguageId, Buffer lpBuffer,
                      int nSize, Pointer va_list);
    


}


