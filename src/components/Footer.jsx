import { Mail, Phone, MapPin } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-blue-900 text-white">
      <div className="max-w-7xl mx-auto px-4 py-12 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 className="text-xl font-bold mb-4">JobSage</h3>
            <p className="text-blue-200">
              Connecting Talent with Opportunity Through AI-Driven Precision
            </p>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Quick Links</h4>
            <ul className="space-y-2">
              <li><a href="/about" className="text-blue-200 hover:text-white transition-colors">About Us</a></li>
              <li><a href="/jobs" className="text-blue-200 hover:text-white transition-colors">Find Jobs</a></li>
              <li><a href="/employers" className="text-blue-200 hover:text-white transition-colors">For Employers</a></li>
              <li><a href="/blog" className="text-blue-200 hover:text-white transition-colors">Career Blog</a></li>
            </ul>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Contact Us</h4>
            <ul className="space-y-2">
              <li className="flex items-center">
                <Mail className="w-5 h-5 mr-2" />
                <span>support@jobsage.com</span>
              </li>
              <li className="flex items-center">
                <Phone className="w-5 h-5 mr-2" />
                <span>+1 (555) 123-4567</span>
              </li>
              <li className="flex items-center">
                <MapPin className="w-5 h-5 mr-2" />
                <span>123 Tech Street, Silicon Valley, CA</span>
              </li>
            </ul>
          </div>
          <div>
            <h4 className="text-lg font-semibold mb-4">Follow Us</h4>
            <div className="flex space-x-4">
              <a href="#" className="text-blue-200 hover:text-white transition-colors">LinkedIn</a>
              <a href="#" className="text-blue-200 hover:text-white transition-colors">Twitter</a>
              <a href="#" className="text-blue-200 hover:text-white transition-colors">Facebook</a>
            </div>
          </div>
        </div>
        <div className="mt-8 pt-8 border-t border-blue-800 text-center text-blue-200">
          <p>&copy; {new Date().getFullYear()} JobSage. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
}