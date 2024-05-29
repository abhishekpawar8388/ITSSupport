// MyUtils.cpp : Defines the entry point for the DLL application.
//

#include <windows.h>
#include <malloc.h>

#include "MyUtils.h"


BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
	case DLL_THREAD_ATTACH:
	case DLL_THREAD_DETACH:
	case DLL_PROCESS_DETACH:
		break;
	}
    return TRUE;
}

// Concatenates its two Unicode string arguments and return a Unicode result.
// Note that this function may cause a memory leak because the result string is not
// dealocated. A better solution would be to use a third parameter for the result
// that is allocated by the caller.
MYUTILS_API wchar_t* Concat(wchar_t* str1, wchar_t* str2)
{
	wchar_t* result = NULL;
   size_t   length, bufSize;

   if (str1 != NULL && str2 != NULL)
   {
      length = wcslen(str1) + wcslen(str2);
      bufSize = (length + 1) * sizeof(wchar_t);
      result = (wchar_t*)malloc(bufSize);
      if (result != NULL)
      {
         ZeroMemory(result, bufSize);
         wcscat(result, str1);
         wcscat(result, str2);
         
      }
   }
   return result;
}