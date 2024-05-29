// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the MYUTILS_EXPORTS
// symbol defined on the command line. this symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// MYUTILS_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#ifdef MYUTILS_EXPORTS
#define MYUTILS_API __declspec(dllexport)
#else
#define MYUTILS_API __declspec(dllimport)
#endif


MYUTILS_API wchar_t* Concat(wchar_t*, wchar_t*);
