import CountdownCircle from './CountdownCircle';

function TotpRow({ account, remaining }) {
   const { issuer, name, code, period = 30 } = account;

   const handleCopy = () => {
      navigator.clipboard.writeText(code);
      // TODO: Add toast notification
   };

   // Format code with space in middle (e.g., "123 456")
   const formatCode = (code) => {
      const mid = Math.floor(code.length / 2);
      return code.slice(0, mid) + ' ' + code.slice(mid);
   };

   return (
      <div className="flex items-center justify-between p-4 bg-white rounded-lg shadow-sm mb-2 hover:shadow-md transition-shadow duration-200">
         <div className="flex flex-col flex-1">
            <span className="text-sm text-gray-500">{issuer}</span>
            <span className="text-base font-semibold text-gray-800">{name}</span>
         </div>

         <div className="flex items-center gap-4">
            <span className="text-2xl font-bold font-mono text-blue-600 tracking-widest">
               {formatCode(code)}
            </span>
            <CountdownCircle remaining={remaining} period={period} />
            <button
               className="p-2 bg-transparent border-none cursor-pointer text-gray-500 rounded-lg hover:bg-gray-100 hover:text-blue-600 transition-all duration-200"
               onClick={handleCopy}
               title="Sao chép mã"
            >
               📋
            </button>
         </div>
      </div>
   );
}

export default TotpRow;
