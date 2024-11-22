import { motion } from 'framer-motion';
import { User, Briefcase, FileText, Settings } from 'lucide-react';

export default function Dashboard() {
  const applications = [
    { id: 1, role: 'Senior Frontend Developer', company: 'Tech Corp', status: 'Under Review' },
    { id: 2, role: 'Full Stack Engineer', company: 'StartUp Inc', status: 'Interview Scheduled' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5 }}
        >
          <h1 className="text-3xl font-bold text-blue-900 mb-8">Dashboard</h1>
          
          {/* Profile Summary */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-8">
            <div className="flex items-center mb-4">
              <User className="w-12 h-12 text-blue-600 mr-4" />
              <div>
                <h2 className="text-xl font-semibold">Welcome back, John Doe</h2>
                <p className="text-gray-600">Frontend Developer | 5 years experience</p>
              </div>
            </div>
          </div>

          {/* Quick Stats */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div className="bg-white rounded-lg shadow-md p-6">
              <Briefcase className="w-8 h-8 text-blue-600 mb-2" />
              <h3 className="text-lg font-semibold mb-1">Applied Jobs</h3>
              <p className="text-2xl font-bold text-blue-600">12</p>
            </div>
            <div className="bg-white rounded-lg shadow-md p-6">
              <FileText className="w-8 h-8 text-blue-600 mb-2" />
              <h3 className="text-lg font-semibold mb-1">Interviews</h3>
              <p className="text-2xl font-bold text-blue-600">3</p>
            </div>
            <div className="bg-white rounded-lg shadow-md p-6">
              <Settings className="w-8 h-8 text-blue-600 mb-2" />
              <h3 className="text-lg font-semibold mb-1">Profile Views</h3>
              <p className="text-2xl font-bold text-blue-600">28</p>
            </div>
          </div>

          {/* Recent Applications */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-4">Recent Applications</h2>
            <div className="space-y-4">
              {applications.map((app) => (
                <motion.div
                  key={app.id}
                  initial={{ x: -20, opacity: 0 }}
                  animate={{ x: 0, opacity: 1 }}
                  className="border-b border-gray-200 pb-4 last:border-0"
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <h3 className="font-semibold text-lg">{app.role}</h3>
                      <p className="text-gray-600">{app.company}</p>
                    </div>
                    <span className="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">
                      {app.status}
                    </span>
                  </div>
                </motion.div>
              ))}
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
}