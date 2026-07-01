function CountdownCircle({ remaining, period = 30 }) {
   const radius = 16;
   const circumference = 2 * Math.PI * radius;
   const progress = (remaining / period) * circumference;

   // Determine color based on remaining time
   let strokeColor = 'stroke-blue-600';
   if (remaining <= 5) {
      strokeColor = 'stroke-red-500';
   } else if (remaining <= 10) {
      strokeColor = 'stroke-amber-500';
   }

   return (
      <div className="relative w-10 h-10">
         <svg width="40" height="40" className="-rotate-90">
            <circle
               className="fill-none stroke-gray-200"
               cx="20"
               cy="20"
               r={radius}
               strokeWidth="3"
            />
            <circle
               className={`fill-none ${strokeColor} transition-all duration-1000 linear`}
               cx="20"
               cy="20"
               r={radius}
               strokeWidth="3"
               strokeLinecap="round"
               strokeDasharray={circumference}
               strokeDashoffset={circumference - progress}
            />
         </svg>
         <span className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-xs font-semibold text-gray-800">
            {remaining}
         </span>
      </div>
   );
}

export default CountdownCircle;
